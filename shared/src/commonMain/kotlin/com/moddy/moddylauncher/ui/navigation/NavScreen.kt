package com.moddy.moddylauncher.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.moddy.moddylauncher.ui.auth.AuthScreen
import com.moddy.moddylauncher.ui.home.HomeScreen
import com.moddy.moddylauncher.ui.instance.InstanceManagerScreen
import com.moddy.moddylauncher.ui.splash.SplashScreen

@Composable
fun NavScreen() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Splash) {

        composable<Splash> {
            SplashScreen(
                navTo = { navController.navigate(it) },
            )
        }

        composable<Home> {
            HomeScreen(
                navController = navController,
                onClickEmptyCard = {
                    navController.navigate(InstanceManager())
                },
                onEditVersionCard = { id ->
                    navController.navigate(InstanceManager(id))
                }
            )
        }

        composable<Auth> {
            AuthScreen(navTo = { navController.navigate(Home) })
        }

        composable<InstanceManager> { navBackStackEntry ->

            val instance = navBackStackEntry.toRoute<InstanceManager>()

            InstanceManagerScreen(
                {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("reload", true)

                    navController.popBackStack()
                },
                instance = instance.id,
            )
        }
    }
}