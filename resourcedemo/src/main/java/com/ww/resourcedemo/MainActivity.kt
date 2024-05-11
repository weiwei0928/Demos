package com.ww.resourcedemo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import leakcanary.LeakCanary
import java.io.File
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val textview: TextView = findViewById<View>(R.id.tv) as TextView

        val resources = loadResource()
        textview.setOnClickListener {
//            textview.text = getString(R.string.plugin_test)
            textview.text = resources.getString(0x7f0f00a7)
        }


    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun loadResource(): Resources {
        val apkPath = filesDir.absolutePath + File.separator + "resourcedemo-debug.apk"
        val file = File(apkPath)
        Log.d(TAG, "APK文件是否存在: ${file.exists()}")
        if (!file.exists()) {
            throw RuntimeException("APK文件不存在,请放到data/data/${applicationInfo.packageName}/files下再进行测试")
        }
        //反射加载资源包
        val newInstance = AssetManager::class.java.newInstance()
        val addAssetPathMethod = AssetManager::class.java.getDeclaredMethod(
            "addAssetPath",
            String::class.java
        )
        addAssetPathMethod.invoke(newInstance, apkPath)
        return Resources(
            newInstance,
            this.resources.displayMetrics,
            this.resources.configuration
        )
    }
}