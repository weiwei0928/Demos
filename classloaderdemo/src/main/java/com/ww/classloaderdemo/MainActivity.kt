package com.ww.classloaderdemo


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "classloaderdemo_MainActivity"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // android 11  且 不是已经被拒绝
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.setData(Uri.parse("package:$packageName"))
                startActivityForResult(intent, 1024)
            }
        }

        val filesDir = filesDir
        Log.d(TAG, "onCreate 111: $filesDir")

        /**
         * targetSdk=34，在安卓14设备运行时 抛出异常  Writable dex file '/data/user/0/com.ww.classloaderdemo/files/classloaderdemo-debug.apk' is not allowed.
         * "应用以Android14为目标平台并使用动态代码加载(DCL)，则所有动态加载的文件都必须标记为只读，否则，系统会抛出异常。"
         *  动态加载dex，路径不能是sdcard,且需要调用此 file.setReadOnly() 代码
         */
        val apkPath: String = filesDir.absolutePath + "/" + "classloaderdemo-debug.apk"
        val apkPath2: String = "/sdcard" + "/ww/" + "classloaderdemo-debug.apk"//安卓14以前可以加载

        findViewById<View>(R.id.btn1).setOnClickListener {
            Toast.makeText(this, "加载dex", Toast.LENGTH_SHORT).show()
//            Utils.loadDexWithClassLoader(this@MainActivity, apkPath)
//            Utils.loadDexWithDexElements(this@MainActivity, apkPath)
            ClassLoaderHelper.loadDexByOverrideFindClass(this@MainActivity, apkPath)
        }

    }
}



