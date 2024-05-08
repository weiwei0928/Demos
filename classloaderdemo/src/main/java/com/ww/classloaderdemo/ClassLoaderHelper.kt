package com.ww.classloaderdemo

import android.content.Context
import android.util.Log
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Objects

object ClassLoaderHelper {

    private const val TAG = "Utils"

    //1、直接使用DexClassLoader或者PathClassLoader加载apk
    fun loadDexWithClassLoader(context: Context, apkPath: String) {
        Log.d(TAG, "loadDexClassLoader....: ")

        // 确保APK文件存在
        val file = File(apkPath)
        Log.d(TAG, "APK文件是否存在: ${file.exists()}")
        if (!file.exists()) {
            Log.d(TAG, "APK文件不存在: ")
            return
        }

        file.setReadOnly()
        Log.d(TAG, "file.absolutePath: ${file.absolutePath} filesDir.absolutePath : ${context.filesDir.absolutePath}")

        try {
            /**
             *  这里使用PathClassLoader/DexClassLoader都能实现动态加载class，区别就是使用PathClassLoader没有第二个参数 即odex路径
             *  在android 8.0 之前会在DexClassLoader 会在odex目录下生成.odex文件，8.0以后第二个参数就废弃了，所以这两个classloader就没有区别了
             */
            val dexClassLoader = DexClassLoader(
                file.absolutePath,      // APK文件路径
                file.absolutePath,  // odex路径
                null,
                context.classLoader.parent
            )

            val className = "com.ww.classloaderdemo.Test"
            val loadedClass = dexClassLoader.loadClass(className)
            val instance = loadedClass.newInstance()
            val method = loadedClass.getDeclaredMethod("test1")
            method.invoke(instance)

        } catch (e: Exception) {
            Log.e(TAG, "loadApkFromSdCard: ", e)
        }
    }

    //2、反射向pathList的成员变量Element[] dexElements 添加我们的dex，常用于热修复
    fun loadDexWithDexElements(context: Context, dexPath: String) {
        Log.d(TAG, "installPatch....: ")

        // 确保APK文件存在
        val file = File(dexPath)
        Log.d(TAG, "APK文件是否存在: ${file.exists()}")
        if (!file.exists()) {
            Log.d(TAG, "APK文件不存在: ")
            return
        }
        file.setReadOnly()
        val cacheDir = context.cacheDir
        val classLoader = context.classLoader
        try {
            val pathListField = Objects.requireNonNull(classLoader.javaClass.superclass)
                .getDeclaredField("pathList")
            pathListField.isAccessible = true
            val pathList = pathListField[classLoader]
            val dexElementsField = pathList.javaClass.getDeclaredField("dexElements")
            dexElementsField.isAccessible = true
            val oldElement = dexElementsField[pathList] as Array<*>
            val files = ArrayList<File>()
            files.add(file)
            //执行makePathElements 让我们的补丁包变成一个 Element[]
            val makePathElements = pathList.javaClass.getDeclaredMethod(
                "makePathElements",
                MutableList::class.java,
                File::class.java,
                MutableList::class.java
            )
            makePathElements.isAccessible = true
            val suppressedExceptions = ArrayList<IOException>()
            val newElement =
                makePathElements.invoke(null, files, cacheDir, suppressedExceptions) as Array<*>
            //要替换系统原本的Element数组的新数组
            val replaceElement = oldElement.javaClass.componentType?.let {
                java.lang.reflect.Array.newInstance(
                    it,
                    oldElement.size + newElement.size
                )
            } as Array<*>

            //补丁包先进去
            System.arraycopy(newElement, 0, replaceElement, 0, newElement.size)
            System.arraycopy(oldElement, 0, replaceElement, newElement.size, oldElement.size)

            dexElementsField[pathList] = replaceElement

            val className = "com.ww.classloaderdemo.Test"
            val clazz = classLoader.loadClass(className)
            val instance = clazz.newInstance()
            val method = clazz.getMethod("test1")
            method.isAccessible  = true
            method.invoke(instance)

        } catch (e: Exception) {
            Log.e(TAG, "installPatch: ", e)
        }
    }

    //3、通过继承系统的ClassLoader(BaseDexClassLoader、DexClassLoader、PathClassLoader)，重写findClass方法，
    // 利用DexFile.loadDex实现动态加载class
    fun loadDexByOverrideFindClass(context: Context, dexPath: String) {
        Log.d(TAG, "loadDexByOverrideFindClass....: ")

        // 确保APK文件存在
        val file = File(dexPath)
        Log.d(TAG, "APK文件是否存在: ${file.exists()}")
        if (!file.exists()) {
            Log.d(TAG, "APK文件不存在: ")
            return
        }
        file.setReadOnly()

        val myClassLoader = MyClassLoader(dexPath,dexPath,null,context.classLoader.parent)

        val clazz = myClassLoader.loadClass("com.ww.classloaderdemo.Test")
        val instance = clazz.newInstance()
        val method = clazz.getMethod("test2")
        method.isAccessible  = true
        method.invoke(instance)
    }

}