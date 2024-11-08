package com.byteclassy.littlelemon.view.components

/*
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
*/
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import com.byteclassy.littlelemon.navigation.Profile
import com.byteclassy.littlelemon.utils.Padding
import com.littlelemon.littlelemon.R

@Composable
fun TopAppBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.HorizontalPadding)
    ) {
//        Spacer(modifier = Modifier.height(Padding.VerticalPadding))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .width(185.dp)
                .aspectRatio(185f / 40f) // maintain the aspect ratio
                .clip(RoundedCornerShape(8.dp))
        )
        Image(
            painter = painterResource(id = R.drawable.user_profile),
            contentDescription = "profile",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(40.dp) // same size for both images
                .clip(CircleShape)
                .clickable { navController.navigate(Profile.route) }
        )
        Spacer(modifier = Modifier.height(Padding.VerticalPadding))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopAppBarPreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context) // Use a TestNavHostController for preview
    TopAppBar(navController)
}
