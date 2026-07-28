package com.moddy.moddylauncher.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moddy.moddylauncher.ui.auth.AuthScreen
import com.moddy.moddylauncher.ui.home.HomeScreen
import com.moddy.moddylauncher.ui.splash.SplashScreen

@Composable
fun NavScreen() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(
                navTo = { navController.navigate(it.route) }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen()
        }

        composable(Screen.Login.route) {
            AuthScreen()
        }
    }
}

enum class Screen(val route: String) {
    Home("home"),
    Login("login"),
    Splash("splash")
}