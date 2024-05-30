package com.ww.demos

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ww.demos.recyclerview.ItemData
import com.ww.demos.recyclerview.MyAdapter


class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var myAdapter: MyAdapter? = null

    companion object {
        private const val TAG = "MainActivity"
    }

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re)

        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView?

        recyclerView?.layoutManager = LinearLayoutManager(this)

        val dataList: MutableList<ItemData> = ArrayList()
        for (i in 1..20) {
            dataList.add(ItemData("Item $i"))
        }

//        myAdapter = MyAdapter(dataList)
        myAdapter = MyAdapter()
        recyclerView?.adapter = myAdapter
        //添加默认的分割线
        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        myAdapter?.submitList(dataList)

        findViewById<View>(R.id.text).setOnClickListener {
            updateData()
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
}



