package com.example.final_part2

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.final_part2.databinding.ActivityEnableBluetoothBinding

class EnableBluetooth : AppCompatActivity() {

    private lateinit var binding: ActivityEnableBluetoothBinding

    companion object {
        private const val REQUEST_ENABLE_BT = 1
        private const val REQUEST_BLUETOOTH_PERMISSION = 2
        private const val REQUEST_ACCESS_COARSE_LOCATION = 3
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEnableBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableBluetooth()
    }

    @SuppressLint("MissingPermission")
    private fun enableBluetooth() {
        Log.d("Function_tag", "Function: enableBluetooth")
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        Log.d("BT_tag", "BT adapter value: $bluetoothAdapter")
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                Log.d("BT_tag", "BT is being enabled")
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            } else {
                Log.d("BT_tag", "BT is already enabled")
                scanBluetoothDevices()
            }
        } else {
            Log.d("BT_tag", "BT is not supported")
        }
    }

    @SuppressLint("MissingPermission")
    private fun scanBluetoothDevices() {
        Log.d("Function_tag", "Function: scanBluetoothDevices")
        val bluetoothManager: BluetoothManager =
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        //val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        Log.d("BT_tag", "BT adapter check: $bluetoothAdapter")
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled) {
            Log.d("BT_tag", "BT conditional 11: bluetoothAdapter")
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                val deviceName = device.name
                val deviceHardwareAddress = device.address // MAC address
                Log.d("BT_tag", "BT devices: $deviceName , $deviceHardwareAddress")
            }

            // List to store the found devices
            val foundDevices = mutableListOf<BluetoothDevice>()

            // Register BroadcastReceiver to receive scan results

            val receiver = object : BroadcastReceiver() {
                @SuppressLint("MissingPermission")
                override fun onReceive(context: Context, intent: Intent) {
                    Log.d("BT_tag", "BT Recieve Function called")
                    val action = intent.action
                    if (BluetoothDevice.ACTION_FOUND == action) {
                        // A Bluetooth device has been found
                        val device =
                            intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        device?.let {
                            // Check if the device is already in the foundDevices list
                            val isDeviceFound = foundDevices.any { existingDevice ->
                                existingDevice.address == device.address
                            }
                            if (!isDeviceFound) {
                                // Store the found device
                                foundDevices.add(device)

                                // Extract information about the device (e.g., name and address)
                                val deviceName: String? = device.name
                                val deviceAddress: String? = device.address

                                // Log the device information
                                Log.d("BT_tag", "Found device - Name: $deviceName, Address: $deviceAddress")
                            }
                        }
                    } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                        Log.d("BT_tag", "BT discovery completed timeout")
                        Log.d("BT_tag", "Found devices:")
                        for (device in foundDevices) {
                            val deviceName: String? = device.name
                            val deviceAddress: String? = device.address
                            Log.d("BT_tag", "Device - Name: $deviceName, Address: $deviceAddress")
                        }
                        var targetDevice: BluetoothDevice? = null
                        val selectedDevice = foundDevices[2] // Get the selected device from the list
                        targetDevice = selectedDevice
                        targetDevice?.let { device ->
                            // Initiate the connection by calling createBond()
                            val result = device.createBond()
                            if (result) {
                                // Connection initiation was successful
                                Log.d("BT_tag", "Connection initiation successful for device: ${device.name}")
                                val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                                bluetoothAdapter?.cancelDiscovery()
                            } else {
                                // Connection initiation failed
                                Log.d("BT_tag", "Connection initiation failed for device: ${device.name}")
                            }
                        }
                    }
                }
            }

            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            this.registerReceiver(receiver, filter)
            val filter2 = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            this.registerReceiver(receiver, filter2)


            // Start device discovery
            Log.d("BT_tag", "BT is starting device discovery")
            bluetoothAdapter.startDiscovery()
            Log.d("BT_tag", "BT is discovering status: ${bluetoothAdapter.isDiscovering}")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("Function_tag", "Function: onRequestPermissionsResult")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("BT_tag", "Bluetooth permission granted")
                // Permission granted, proceed with enabling Bluetooth
                enableBluetooth()
            } else {
                Log.d("BT_tag", "Bluetooth permission denied")
                // Permission denied, handle accordingly
            }
        } else if (requestCode == REQUEST_ACCESS_COARSE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("BT_tag", "Location permission granted")
                // Permission granted, start device discovery
                scanBluetoothDevices()
            } else {
                Log.d("BT_tag", "Location permission denied")
                // Permission denied, handle accordingly
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("Function_tag", "Function: onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("BT_tag", "Bluetooth has been enabled")
                // Bluetooth has been enabled
                // Proceed with further Bluetooth operations
                scanBluetoothDevices()
            } else {
                Log.d("BT_tag", "Bluetooth enabling has been cancelled or failed")
                // Bluetooth enabling has been cancelled or failed
                // Handle accordingly (e.g., show an error message)
            }
        }
    }

    override fun onDestroy() {
        Log.d("Function_tag", "Function: onDestroy")
        super.onDestroy()
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("BT_tag", "permissions check on discovery")
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        bluetoothAdapter?.cancelDiscovery()
    }
}
