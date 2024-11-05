package com.littlelemon.littlelemon.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.littlelemon.littlelemon.ui.screens.HomeScreen
import com.littlelemon.littlelemon.ui.screens.OnboardingScreen
import com.littlelemon.littlelemon.ui.screens.ProfileScreen


@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isUserOnboarded: Boolean
) {
    val startDestination = if (isUserOnboarded) Home.route else Onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Onboarding.route) { OnboardingScreen(navController) }
        composable(Home.route) { HomeScreen(navController) }
        composable(Profile.route) { ProfileScreen(navController) }
    }
}
