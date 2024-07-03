package com.ww.demos

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.ww.demos.recyclerview.ItemData
import com.ww.demos.recyclerview.MyAdapter


class MainActivity : AppCompatActivity() {

    private var height: Int = 0
    private var buttonX = 0
    private var buttonY = 0
    private var popupX = 0
    private var popupY = 0
    private var recyclerView: RecyclerView? = null
    private var myAdapter: MyAdapter? = null

    companion object {
        private const val TAG = "ww-MainActivity"
    }

    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView?
//
//        recyclerView?.layoutManager = LinearLayoutManager(this)
//
//        val dataList: MutableList<ItemData> = ArrayList()
//        for (i in 1..20) {
//            dataList.add(ItemData("Item $i"))
//        }
//
////        myAdapter = MyAdapter(dataList)
//        myAdapter = MyAdapter()
//        recyclerView?.adapter = myAdapter
//        //添加默认的分割线
//        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//        myAdapter?.submitList(dataList)
//
//        findViewById<View>(R.id.text).setOnClickListener {
//            updateData()
//        }
        val inflater = LayoutInflater.from(this)
        val popupWindow = PopupWindow(this)
//        popupWindow.width = 80
//        popupWindow.height = 80
        popupWindow.setContentView(inflater.inflate(R.layout.course_popup_layout, null))
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val textView = findViewById<View>(R.id.textView)
        val view: View = findViewById<View>(R.id.popup)


        textView.post {
            val location = IntArray(2)
            textView.getLocationOnScreen(location)
            buttonX = location[0]
            buttonY = location[1]
            val buttonWidth: Int = textView.width
            val viewWidth: Int = view.width
            val buttonHeight: Int = textView.height
            val viewHeight: Int = view.height

            // 计算PopupWindow显示的位置

            // 计算PopupWindow显示的位置
            val popupHeight: Int = dp2px(resources,72)
            val popupWidth: Int =dp2px(resources,106)
            popupX = buttonX + buttonWidth / 2-dp2px(resources,22)
            popupY = buttonY -popupHeight
            Log.d(TAG, "onCreate height: ${textView.height}")
            Log.d(TAG, "onCreate width: ${textView.width}")
            Log.d(TAG, "onCreate x: $buttonX")
            Log.d(TAG, "onCreate y: $buttonY")
            Log.d(TAG, "onCreate viewWidth: $viewWidth")
            Log.d(TAG, "onCreate viewHeight: $viewHeight")
            Log.d(TAG, "onCreate popupWidth: $popupWidth")
            Log.d(TAG, "onCreate popupHeight: $popupHeight")
            popupWindow.isOutsideTouchable = true;

        }
//        onCreate height: 122
//        onCreate width: 371
//        onCreate x: 355
//        onCreate y: 1267

        textView.setOnClickListener {
            popupWindow.showAsDropDown(textView, 200, -300, Gravity.NO_GRAVITY);
        }
    }

    private fun updateData() {
        val newDataList: MutableList<ItemData> = ArrayList()
        for (i in 1..5) {
            newDataList.add(ItemData("Updated Item $i"))
        }
        // 使用submitList来更新数据和自动刷新RecyclerView
        myAdapter!!.submitList(newDataList)
    }
    private fun dp2px(resources: Resources, dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}



