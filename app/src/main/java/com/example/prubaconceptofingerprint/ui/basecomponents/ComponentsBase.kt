package com.example.prubaconceptofingerprint.ui.basecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonBase(label: String, loginEnable: Boolean, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        shape = CutCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF3363DB),
            disabledBackgroundColor = Color(0xFF6E96FC)
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        enabled = loginEnable
    )
    {
        TextBase(label = label)
    }
}

@Composable
fun TextBase(label: String) {
    Text(
        text = label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFE2E2E2)
    )
}

@Composable
fun TextFieldBaseEmail(
    placeHolderText: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier,
    email: String,
    onTextFieldChanged: (String) -> Unit
) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = modifier,
        placeholder = { Text(text = placeHolderText) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF000000),
            backgroundColor = Color(0xFFE8F2F7)
        )
    )
}

@Composable
fun TextFieldBasePass(
    placeHolderText: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier,
    password: String,
    onTextFieldChanged: (String) -> Unit
) {
    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        modifier = modifier,
        placeholder = { Text(text = placeHolderText) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF000000),
            backgroundColor = Color(0xFFE8F2F7)
        )
    )
}

@Composable
fun ImageBase(modifier: Modifier, image: Int) {
    Image(
        painterResource(id = image),
        contentDescription = "pet book",
        modifier = modifier
    )
}
