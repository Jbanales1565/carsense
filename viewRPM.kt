package com.example.final_part2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.final_part2.databinding.ActivityViewRpmBinding

class viewRPM : AppCompatActivity() {
    lateinit var binding: ActivityViewRpmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewRpmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create an instance of AnalogGaugeView
        //val analogGaugeView = AnalogGaugeView(this)

        // Add the AnalogGaugeView to the CardView container
        //binding.cardview1.addView(analogGaugeView)
    }
}
