package com.ww.skin

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater.Factory2
import android.view.View
import java.lang.reflect.Constructor
import java.util.Observable
import java.util.Observer

/**
 * @Author weiwei
 * @Date 2024/10/8 23:20
 */

class SkinLayoutInflaterFactory(private val activity: Activity) : Factory2, Observer {
    private val skinAttribute: SkinAttribute

    //当选择新皮肤后需要替换view与之对应的属性
    //页面属性管理器
    init {
        skinAttribute = SkinAttribute()
    }

    override fun update(o: Observable, arg: Any) {
        skinAttribute.applySkin()
    }


    /**
     * 创建对应布局并且返回
     *
     * @param parent  当前TAG父布局
     * @param name    在布局中的TAG，如TextView
     * @param context 上下文
     * @param attrs   对应TAG中的属性，如android:text
     * @return View      null则由系统创建
     */
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        //换肤就是在需要时替换view的属性（src、background等）
        //所以这里创建view，从而修改view属性
        var view = createSDKView(name, context, attrs)
        if (null == view) {
            view = createView(name, context, attrs)
        }
        if (null != view) {
            Log.v("skin", String.format("[%s] 筛选：%s", context.javaClass.name, name))
            //加载属性
            skinAttribute.look(view, attrs)
        }
        return view
    }

    private fun createView(name: String, context: Context, attrs: AttributeSet): View? {
        val constructor = findConstructor(context, name)
        try {
            return constructor!!.newInstance(context, attrs)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun findConstructor(context: Context, name: String): Constructor<out View>? {
        var constructor = sConstructorMap[name]
        Log.v("skin", "findConstructor  $name")
        if (constructor == null) {
            try {
                val clazz = context.classLoader.loadClass(name).asSubclass(
                    View::class.java
                )
                constructor = clazz.getConstructor(*mConstructorSignature)
                sConstructorMap[name] = constructor
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return constructor
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    private fun createSDKView(name: String, context: Context, attrs: AttributeSet): View? {
        //如果包含，则不是SDK中的view 可能是自定义view包括support库中的view
        if (name.contains(".")) {
            return null
        }
        //不包含就要在解析的及诶单name前拼上：android.widget等尝试去反射
        for (prefix in prefixArray) {
            val view = createView(prefix + name, context, attrs)
            if (view != null) return view
        }
        return null
    }

    companion object {
        //安卓里面控件的包名,这个变量是为了下面代码里，反射创建类的class而预备的
        private val prefixArray = arrayOf(
            "android.widget.",
            "android.view.",
            "android.webkit.",
            "android.app."
        )

        //记录对应view的构造函数
        private val mConstructorSignature = arrayOf(
            Context::class.java,
            AttributeSet::class.java
        )

        //用映射，将View的反射构造函数都存起来
        private val sConstructorMap = HashMap<String, Constructor<out View>?>()
    }
}