package ua.vitolex.cypher.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.vitolex.cypher.presentation.screens.history.HistoryScreen
import ua.vitolex.cypher.presentation.screens.main.MainScreen


sealed class Screens(val rout: String) {
    object SplashScreen : Screens(rout = "splash_screen")
    object MainScreen : Screens(rout = "main_screen")
    object HistoryScreen : Screens(rout = "history_screen")
}

@Composable
fun SetupNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.MainScreen.rout) {
//        composable(route = Screens.SplashScreen.rout) {
//            SplashScreen(navController = navController)
//        }
        composable(route = Screens.MainScreen.rout) {
            MainScreen(navController = navController)
        }
        composable(route = Screens.HistoryScreen.rout) {
            HistoryScreen(navController = navController)
        }
    }
}