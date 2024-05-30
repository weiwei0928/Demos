package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.test.leetcode.Test17

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var array :IntArray = intArrayOf(1,2,3)

//        Test17.permute(array)
    }
}
