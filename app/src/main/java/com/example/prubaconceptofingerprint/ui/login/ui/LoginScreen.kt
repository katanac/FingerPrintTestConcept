package com.example.prubaconceptofingerprint.ui.login.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.prubaconceptofingerprint.R
import com.example.prubaconceptofingerprint.ui.basecomponents.ButtonBase
import com.example.prubaconceptofingerprint.ui.basecomponents.ImageBase
import com.example.prubaconceptofingerprint.ui.basecomponents.TextFieldBaseEmail
import com.example.prubaconceptofingerprint.ui.basecomponents.TextFieldBasePass

@Composable
fun LoginScreen(loginVM: LoginVM, context: Context) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(10.dp)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Login(Modifier.align(Alignment.Center), loginVM, context)
    }
}

@Composable
fun Login(modifier: Modifier, loginVM: LoginVM, context: Context) {

    val email: String by loginVM.email.observeAsState("")
    val password: String by loginVM.password.observeAsState("")
    val loginEnable: Boolean by loginVM.loginEnable.observeAsState(false)

    Column(modifier = modifier) {
        ImageBase(
            Modifier
                .align(Alignment.CenterHorizontally)
                .size(250.dp), R.drawable.img_1
        )

        TextFieldBaseEmail(
            "Usuario",
            KeyboardOptions(keyboardType = KeyboardType.Email),
            Modifier.width(300.dp),
            email
        ) { loginVM.onLoginChanged(it, password) }

        Spacer(modifier = Modifier.padding(12.dp))

        TextFieldBasePass(
            "Contraseña",
            KeyboardOptions(keyboardType = KeyboardType.Password),
            Modifier.width(300.dp), password
        ) { loginVM.onLoginChanged(email, it) }
        Spacer(modifier = Modifier.padding(8.dp))

        ButtonBase("INGRESAR", loginEnable) {
            loginVM.settingAccelerometer(context)
            loginVM.onRequestPermissionsFingerprint(context)
        }
    }
}
