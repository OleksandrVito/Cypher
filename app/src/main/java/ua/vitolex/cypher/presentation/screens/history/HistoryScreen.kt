package ua.vitolex.cypher.presentation.screens.history

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ua.vitolex.cypher.presentation.screens.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    //для центрування тексту в топбарі
    val modifier = Modifier
        .size(width = 90.dp, height = 50.dp)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cypher",
                        style = MaterialTheme.typography.h1,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(start = 20.dp, top = 2.dp, bottom = 10.dp, end = 0.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    Box(modifier = modifier, contentAlignment = Alignment.Center) {
                        Spacer(modifier = Modifier.width(22.dp))
                       Button(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .scale(0.8f)
                        ) {
                           Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                       }
                    }
                },
                contentColor = Color.Red,
                backgroundColor = Color.Green,
                modifier = Modifier
                    .height(60.dp)
            )
        }
    ) {
        Text(text = "History", fontSize = 48.sp)
    }
}