package com.moddy.moddylauncher.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moddy.moddylauncher.ui.auth.AuthScreen
import com.moddy.moddylauncher.ui.home.HomeScreen
import com.moddy.moddylauncher.ui.instance.InstanceManagerScreen
import com.moddy.moddylauncher.ui.splash.SplashScreen

@Composable
fun NavScreen(
    startDestination: String = Screen.Splash.route,
) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = startDestination) {

        composable(Screen.Splash.route) {
            SplashScreen(
                navTo = { navController.navigate(it.route) }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onClickEmptyCard = {
                    navController.navigate(Screen.InstanceManager.route)
                }
            )
        }

        composable(Screen.Login.route) {
            AuthScreen(navTo = { navController.navigate(Screen.Home.route) })
        }

        composable(
            Screen.InstanceManager.route
        ) {
            InstanceManagerScreen(
                { navController.popBackStack() }
            )
        }
    }
}

enum class Screen(val route: String) {
    Home("home"),
    Login("login"),
    Splash("splash"),
    InstanceManager("instance_manager")
}