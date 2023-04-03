package ua.vitolex.cypher.presentation.screens.history

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ua.vitolex.cypher.R
import ua.vitolex.cypher.domain.model.Message
import ua.vitolex.cypher.presentation.components.DeleteDialog
import ua.vitolex.cypher.presentation.components.MessageItem
import ua.vitolex.cypher.presentation.components.SearchBar
import ua.vitolex.cypher.presentation.screens.MainViewModel
import ua.vitolex.cypher.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    //для центрування тексту в топбарі
    val modifier = Modifier
        .size(width = 90.dp, height = 50.dp)

    val messages = viewModel.messages.collectAsState(initial = emptyList())
    val openDialog = remember {
        mutableStateOf(false)
    }
    val deleteText = remember {
        mutableStateOf("")
    }
    val messagesToDelete = remember {
        mutableStateOf(listOf<Message>())
    }
    val messageQuery = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    var toast: Toast? = null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cypher",
                        style = MaterialTheme.typography.h1,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 4.dp, top = 0.dp, bottom = 0.dp, end = 0.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }, modifier = Modifier
                            .size(width = 70.dp, height = 50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back
                            ),
                            modifier = Modifier.scale(1.2f)
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier.scale(0.9f)
                                .size(width = 70.dp, height = 50.dp),
                        onClick = {
                            if (messages.value?.isNotEmpty() == true) {
                                openDialog.value = true
                                deleteText.value =
                                    context.getString(R.string.delete_all_notes_message)
                                messagesToDelete.value = messages.value ?: emptyList()
                            } else {
                                if (toast != null) {
                                    toast?.cancel()
                                }
                                toast = Toast.makeText(
                                    context,
                                    context.getString(R.string.no_notes_found),
                                    Toast.LENGTH_SHORT
                                )
                                toast?.show()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete),
                            tint = MyOrange,
                            modifier = Modifier.scale(1.5f)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                },
                contentColor = MyOrange,
                backgroundColor = MainColor,
                modifier = Modifier
                    .height(65.dp)
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(MyGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MyGray)
        ) {
            SearchBar(query = messageQuery)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier
                    .background(MyGray)
                    .fillMaxSize()
                    .weight(1f)
            ) {
                val queriedNotes = if (messageQuery.value.isEmpty()) {
                    messages.value
                } else {
                    messages.value.filter { it.encryptMessage.contains(messageQuery.value) }
                }

                itemsIndexed(queriedNotes) { index, message ->

                    MessageItem(
                        message = message,
                        openDialog = openDialog,
                        deleteText = deleteText,
                        messagesToDelete = messagesToDelete,
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(60.dp)
//                    .background(Color.Red)
//            )
        }
        DeleteDialog(
            openDialog = openDialog,
            text = deleteText,
            action = {
                messagesToDelete.value.forEach {
                    viewModel.deleteMessage(it)
                }
            },
            messagesToDelete = messagesToDelete
        )
    }
}
