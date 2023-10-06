package com.example.prubaconceptofingerprint.ui.login.ui

import android.Manifest
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.lifecycle.*
import com.example.prubaconceptofingerprint.ui.basecomponents.Constants
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import fingerprint.plusti.com.fingerprintdevice.Handler.FingerprintCreate

class LoginVM : ViewModel(), LifecycleObserver {

    //region Atributes
    private var sensorManager: SensorManager? = null
    private var accelerometerSensor: Sensor? = null
    private var accelerometerPresent = false
    private var preassure1 = 0.1f
    private var preassure2 = 0.1f
    private var fingerprint: String? = null

    private var xSensor = 0.0f
    private var ySensor = 0.0f
    private var zSensor = 0.0f


    //endregion

    //region states live data
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable
    //endregion

    //region private funtions
    private fun isValidPassword(password: String): Boolean = password.length > 3

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun onRequestPermissionsFingerprint(context: Context) {

        val permissionBiometric = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Manifest.permission.USE_BIOMETRIC
        } else {
            Manifest.permission.USE_FINGERPRINT
        }
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                permissionBiometric,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    sensorManager!!.unregisterListener(accelerometerListener)
                    Thread {
                        sensorManager!!.unregisterListener(accelerometerListener)
                        createFingerPrintDevice(context)
                    }.start()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?,
                ) {
                    sensorManager!!.unregisterListener(accelerometerListener)
                    createFingerPrintDevice(context)
                }
            }).check()
    }

    private fun createFingerPrintDevice(context: Context) {
        val fingerprintCreate = FingerprintCreate()
        try {
            fingerprint = fingerprintCreate.generateFingerpintKey(
                Constants.FINGERPRINT_MONITOR_PLUS_DASHBOARD,
                ySensor,
                zSensor,
                preassure1,
                preassure2,
                context,
                Constants.FINGERPRINT_MONITOR_PLUS_IS_OVERLAY,
                Constants.FINGERPRINT_MONITOR_PLUS_API_KEY,
            )
            Log.i("Fingerprint", fingerprint.toString())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun settingAccelerometer(context: Context) {
        sensorManager = context.getSystemService(ComponentActivity.SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager!!.getSensorList(Sensor.TYPE_ACCELEROMETER)
        if (sensorList.isNotEmpty()) {
            accelerometerPresent = true
            accelerometerSensor = sensorList[0]
        } else {
            accelerometerPresent = false
        }
    }

    //endregion

    //region Life Cycle Main Activity
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun connectListener() {

        if (accelerometerPresent) {
            sensorManager?.registerListener(
                accelerometerListener,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL,
            )
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun disconnectListener() {
        if (accelerometerPresent) {
            sensorManager!!.unregisterListener(accelerometerListener)
        }
    }
    //endregion

    //region Events/Listeners
    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private val accelerometerListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(arg0: Sensor, arg1: Int) {
            // TODO Auto-generated method stub
        }

        override fun onSensorChanged(arg0: SensorEvent) {
            xSensor = arg0.values[0]
            ySensor = arg0.values[1]
            zSensor = arg0.values[2]
        }
    }

    //endregion

}