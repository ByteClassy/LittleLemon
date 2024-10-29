package com.littlelemon.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(navController: NavHostController) {
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var registrationMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val commonModifier = Modifier
        .padding(top = 8.dp, bottom = 10.dp)
        .height(56.dp)
        .fillMaxWidth()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .size(150.dp)
        )
        Text(
            text = stringResource(id = R.string.lets_get_to_know_you),
            fontSize = 24.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(40.dp),
            color = Color.White
        )
        Text(
            text = stringResource(R.string.personal_information),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 40.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = stringResource(R.string.first_name))
            CustomTextField(
                value = firstName,
                onValueChange = { firstName = it },
                modifier = commonModifier,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = firstNameError
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(R.string.last_name))
            CustomTextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = commonModifier,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = lastNameError
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(R.string.email))
            CustomTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                modifier = commonModifier,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                isError = emailError
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = registrationMessage,
                color = if (registrationMessage.contains("unsuccessful")) Color.Red else Color.Green,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = {
                    emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()
                    firstNameError = firstName.text.isBlank()
                    lastNameError = lastName.text.isBlank()

                    if (firstNameError || lastNameError || email.text.isBlank() || emailError) {
                        registrationMessage = "Registration unsuccessful. Please enter all data."
                    } else {
                        val sharedPreferences =
                            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("first_name", firstName.text)
                            putString("last_name", lastName.text)
                            putString("email", email.text)
                            putBoolean("is_onboarded", true)
                            apply()
                        }
                        registrationMessage = "Registration successful!"
                        coroutineScope.launch {
                            delay(2000) // Delay to allow users to read the message
                            navController.navigate("home")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
                shape = RoundedCornerShape(10.dp),
                modifier = commonModifier
            ) {
                Text(
                    text = stringResource(R.string.register),
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier,
    keyboardOptions: KeyboardOptions,
    isError: Boolean = false
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .border(
                width = 1.dp,
                color = if (isError) Color.Red else Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp),
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingScreenPreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context) // Use a TestNavHostController for preview
    OnboardingScreen(navController = navController)
}
