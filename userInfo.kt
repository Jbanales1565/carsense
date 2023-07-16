package com.example.final_part2

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.final_part2.databinding.ActivityUserInfoBinding

class userInfo : AppCompatActivity() {
    lateinit var binding: ActivityUserInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_view_show_data)
        val binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}