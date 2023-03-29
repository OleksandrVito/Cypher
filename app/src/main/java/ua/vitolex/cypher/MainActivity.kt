package ua.vitolex.cypher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.vitolex.cypher.presentation.navigation.SetupNavHost
import ua.vitolex.cypher.presentation.screens.main.MainScreen
import ua.vitolex.cypher.ui.theme.CypherTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CypherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    MainScreen(navController = navController)
                    SetupNavHost(navController = navController)
                }
            }
        }
//        MobileAds.initialize(this)
    }
}
