package ua.vitolex.cypher.presentation.screens.main


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ua.vitolex.cypher.R
import ua.vitolex.cypher.presentation.navigation.Screens
import ua.vitolex.cypher.presentation.screens.MainViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController) {
    var inputValue by remember {
        mutableStateOf("")
    }
    var outputValue by remember {
        mutableStateOf("")
    }

    var key by remember {
        mutableStateOf("1")
    }


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
                        textAlign = TextAlign.Start
                    )
                },
                actions = {
                    Box(contentAlignment = Alignment.Center) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                modifier = Modifier.scale(0.9f),
                                onClick = {
                                    navController.navigate(Screens.HistoryScreen.rout)
                                }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_history_24),
                                    contentDescription = "history"
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                modifier = Modifier.scale(0.9f),
                                onClick = {
                                    navController.navigate(Screens.SettingsScreen.rout)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "settings"
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
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
        Column(
            Modifier
                .padding(32.dp)
                .fillMaxSize()
        ) {
            TextField(
                value = key,
                onValueChange = { key = it },
                label = { Text(text = "Key") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {

            }) {
                Text(text = "Generate key")
            }
            TextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                label = { Text(text = "Input") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = {
                    outputValue = viewModel.encryptMessage(
                        inputValue, key
                    )
                }) {
                    Text(text = "Encrypt")
                }

                Button(onClick = {
                    outputValue = viewModel.decryptMessage(
                        inputValue,  key
                    )
                }) {
                    Text(text = "Decrypt")
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(
                value = outputValue,
                onValueChange = { outputValue = it },
                label = { Text(text = "Result") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}