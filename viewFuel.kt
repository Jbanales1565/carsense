package com.example.final_part2

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.final_part2.databinding.ActivityViewFuelBinding

class viewFuel : AppCompatActivity() {
    lateinit var binding: ActivityViewFuelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_view_show_data)
        val binding = ActivityViewFuelBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnShownames.setOnClickListener {
            val ds = DataSource(this)
            ds.open()
            val showNames = ds.getAllShowNames()
            Log.d("BLAH", "customerNames: ${showNames.joinToString(", ")}")
            val adapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1, showNames)

        }
    }
}