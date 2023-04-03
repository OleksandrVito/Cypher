package ua.vitolex.cypher.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ua.vitolex.cypher.R
import ua.vitolex.cypher.domain.model.Message
import ua.vitolex.cypher.ui.theme.MainColor
import ua.vitolex.cypher.ui.theme.MyBlack
import ua.vitolex.cypher.ui.theme.MyWhite

@Composable
fun DeleteDialog(
    openDialog: MutableState<Boolean>,
    text: MutableState<String>,
    action: () -> Unit,
    messagesToDelete: MutableState<List<Message>>,
) {
    if (openDialog.value) {
        AlertDialog(
            backgroundColor = MyWhite,
            onDismissRequest = { openDialog.value = false },
            title = {
                Text(text = text.value, color = MyBlack, modifier = Modifier.padding(bottom = 10.dp))
            },

            buttons = {
                Row(
                    Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column() {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MainColor,
                                contentColor = MyWhite
                            ),
                            onClick = {
                                action.invoke()
                                openDialog.value = false
                                messagesToDelete.value = mutableListOf()
                            }
                        ) {
                            Text(text = stringResource(R.string.yes))
                        }
                        Spacer(modifier = Modifier.padding(2.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MainColor,
                                contentColor = MyWhite
                            ),
                            onClick = {
                                openDialog.value = false
                                messagesToDelete.value = mutableListOf()
                            }
                        ) {
                            Text(text = stringResource(R.string.no))
                        }
                    }
                }
            }
        )
    }
}