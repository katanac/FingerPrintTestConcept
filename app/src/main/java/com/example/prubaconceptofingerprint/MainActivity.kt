package com.example.prubaconceptofingerprint

import android.Manifest
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.prubaconceptofingerprint.ui.basecomponents.Constants.FINGERPRINT_MONITOR_PLUS_API_KEY
import com.example.prubaconceptofingerprint.ui.basecomponents.Constants.FINGERPRINT_MONITOR_PLUS_DASHBOARD
import com.example.prubaconceptofingerprint.ui.basecomponents.Constants.FINGERPRINT_MONITOR_PLUS_IS_OVERLAY
import com.example.prubaconceptofingerprint.ui.login.ui.LoginScreen
import com.example.prubaconceptofingerprint.ui.login.ui.LoginVM
import com.example.prubaconceptofingerprint.ui.theme.PrubaConceptoFingerPrintTheme
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import fingerprint.plusti.com.fingerprintdevice.Handler.FingerprintCreate

class MainActivity : ComponentActivity() {


    private var sensorManager: SensorManager? = null
    private var accelerometerSensor: Sensor? = null
    private var accelerometerPresent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            lifecycle.addObserver(LoginVM())

            PrubaConceptoFingerPrintTheme {
                // A surface container using the 'background' color from the theme
                LoginScreen(LoginVM(), this@MainActivity)
            }
        }

    }



}

