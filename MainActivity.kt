package com.example.final_part2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_part2.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnViewRPM.setOnClickListener{
            startActivity(Intent(this, viewRPM::class.java))
        }
        binding.btnViewFuel.setOnClickListener{
            startActivity(Intent(this, viewFuel::class.java))
        }
        binding.btnBluetooth.setOnClickListener{
            startActivity(Intent(this, EnableBluetooth::class.java))
        }
        binding.btnMongoDB.setOnClickListener{
            startActivity(Intent(this, MongoDBHandler::class.java))
        }
        binding.btnConnectDevices.setOnClickListener{
            startActivity(Intent(this, connectDevices::class.java))
        }
        binding.btnUserInfo.setOnClickListener{
            startActivity(Intent(this, userInfo::class.java))
        }

    }
}