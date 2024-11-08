package com.byteclassy.littlelemon.view.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import com.byteclassy.littlelemon.navigation.Onboarding
import com.byteclassy.littlelemon.utils.Padding
import com.littlelemon.littlelemon.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavHostController) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val firstName =
        remember { mutableStateOf(sharedPreferences.getString("first_name", "") ?: "") }
    val lastName =
        remember { mutableStateOf(sharedPreferences.getString("last_name", "") ?: "") }
    val email =
        remember { mutableStateOf(sharedPreferences.getString("email", "") ?: "") }
    var logoutMessage by remember { mutableStateOf("") }

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
        Spacer(modifier = Modifier.height(Padding.VerticalPadding))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .width(185.dp)
                .aspectRatio(185f / 40f) // maintain the aspect ratio
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.personal_information),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Padding.HorizontalPadding, top = 40.dp),
        )
        Spacer(modifier = Modifier.height(Padding.VerticalPadding))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.HorizontalPadding)
        ) {
            Text(text = stringResource(R.string.first_name))
            CustomText(
                firstName.value,
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(Padding.VerticalPadding))
            Text(text = stringResource(R.string.last_name))
            CustomText(
                lastName.value,
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(Padding.VerticalPadding))
            Text(text = stringResource(R.string.email))
            CustomText(
                email.value,
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(Padding.VerticalPadding))
            Text(
                text = logoutMessage,
                color = Color(0xFF138808),
                style = TextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = {
                    sharedPreferences.edit().clear().apply()
                    logoutMessage = "You have logged out successfully."
                    coroutineScope.launch {
                        delay(2000) // Delay to allow users to read the message
                        navController.navigate(Onboarding.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
                shape = RoundedCornerShape(10.dp),
                modifier = commonModifier
            ) {
                Text(
                    text = stringResource(R.string.log_out),
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(Padding.VerticalPadding))
        }
    }
}

@Composable
fun CustomText(
    text: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context) // Use a TestNavHostController for preview
    ProfileScreen(navController = navController)
}
