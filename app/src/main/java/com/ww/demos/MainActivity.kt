package com.ww.demos

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.io.File
import java.lang.reflect.Field


class MainActivity : AppCompatActivity() {

    private var isRefuse: Boolean = false

    companion object {
        private const val TAG = "test2"
        private const val PERMISSION_REQUEST_CODE = 100
        private val REQUIRED_PERMISSIONS = arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
    }
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // android 11  且 不是已经被拒绝
            // 先判断有没有权限
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.setData(Uri.parse("package:$packageName"))
                startActivityForResult(intent, 1024)
            }
        }

        val filesDir = filesDir
        val absolutePath = filesDir.absolutePath
        val dataDir = dataDir
        Log.d(TAG, "onCreate 111: $filesDir")
        Log.d(TAG, "onCreate 222: $absolutePath")
        Log.d(TAG, "onCreate 222: $dataDir")

        val apkPath: String = filesDir.absolutePath + "/" + "app-debug.apk"
        val apkPath2: String = "/sdcard" + "/ww/" + "ww.apk"
//        val apkPath: String = filesDir.absolutePath
        val optPath = filesDir.absolutePath
        val optPath2 = "/sdcard/ww"

        val dexOutputDir: File = getDir("dex", MODE_PRIVATE)
        Log.d(TAG, "onCreate 222: $dexOutputDir")

        val apkFile: File = File(apkPath2)
        Log.d(TAG, "onCreate 3333: ${apkFile.exists()}")

        // 构造DexClassLoader

        // 构造DexClassLoader
//        val clsLoader = DexClassLoader(
//            apkFile.absolutePath,  // .apk文件的路径
//            optPath2,  // Dex解压输出目录
//            null,
//            classLoader // 当前应用的ClassLoader，可选，用于加载依赖
//        )

        findViewById<View>(R.id.textView).setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
//            try {
//                val cls = clsLoader
//                    .loadClass("com.ww.demos.Test")
//                val obj = cls.newInstance()
//                val invokeMethod = cls.getDeclaredMethod("test2")
//                invokeMethod.isAccessible = true
//                invokeMethod.invoke(obj)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }

            loadApkFromSdCard(this@MainActivity,apkPath2)
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限授予成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 检查是否已获得所有所需的权限
    private fun hasPermissions(context: AppCompatActivity, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }


    fun loadApkFromSdCard(context: Context, apkPath: String) {
        Log.d(TAG, "PathClassLoader: ")


        // 确保APK文件存在
        val file = File(apkPath)

        Log.d(TAG, "APK文件是否存在: ${file.exists()}")

        if (!file.exists()) {
            Log.d(TAG, "APK文件不存在: ")
            return
        }
        val dexOutputDir: File = context.getDir("dex", 0) //
        Log.d(TAG, "loadApkFromSdCard: ${dexOutputDir.absolutePath}")


        try {
            // 初始化DexClassLoader，参数分别为：包含dex的文件或目录路径，解压生成的dex存储目录，包含库文件的目录，父ClassLoader
            val dexClassLoader = DexClassLoader(
                file.absolutePath,      // APK文件路径
                file.absolutePath,  // Dex文件解压后的存储位置
                null,          // 如果APK中包含.so文件，这里指定其路径，一般为null表示不加载.so
                context.classLoader       // 父ClassLoader
            )

//            inspectDexClassLoader(dexClassLoader)

            val className = "com.ww.demos.Test"

            // 使用DexClassLoader加载指定类
            val loadedClass = dexClassLoader.loadClass(className)

//            // 使用反射来创建类的实例
            val instance = loadedClass.newInstance()

            // 假设MyClass有一个名为myMethod的无参方法
            val method = loadedClass.getMethod("test2")
            method.invoke(instance)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun inspectDexClassLoader(dexClassLoader: DexClassLoader) {
        try {
            val pathListField: Field = BaseDexClassLoader::class.java.getDeclaredField("pathList")
            pathListField.setAccessible(true)
            val pathList: Any = pathListField.get(dexClassLoader)
            val dexElementsField: Field = pathList.javaClass.getDeclaredField("dexElements")
            dexElementsField.setAccessible(true)
            val any = dexElementsField.get(pathList) as Array<*>
            for (dexElement in any) {
                val dexFileField: Field = dexElement!!.javaClass.getDeclaredField("dexFile")
                dexFileField.setAccessible(true)
                println("test  Loaded DexFile: " + dexFileField.get(dexElement).javaClass.name)
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    // 带回授权结果
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1024 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 检查是否有权限
            if (Environment.isExternalStorageManager()) {
                isRefuse = false
                // 授权成功
            } else {
                isRefuse = true
                // 授权失败
            }
        }
    }
}



