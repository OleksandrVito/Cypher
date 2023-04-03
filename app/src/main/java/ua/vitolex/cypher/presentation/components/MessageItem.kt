package ua.vitolex.cypher.presentation.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.vitolex.cypher.R
import ua.vitolex.cypher.domain.model.Message
import ua.vitolex.cypher.ui.theme.MyBlack
import ua.vitolex.cypher.ui.theme.SecondaryGray

@Composable
fun MessageItem(
    message: Message,
    openDialog: MutableState<Boolean>,
    deleteText: MutableState<String>,
    messagesToDelete: MutableState<List<Message>>,
) {
    val context = LocalContext.current
    var toast: Toast? = null

    //для копіювання тексту
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    //для шеру тексту
    fun shareTextOrLink(context: Context, text: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        try {
            context.startActivity(shareIntent)
        } catch (_: Exception) {
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 5.dp,
    ) {
        Column(
            modifier = Modifier
                .background(SecondaryGray.copy(0.5f))
//                .height(120.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = message.encryptMessage,
                    color = MyBlack,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = 12.dp,
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 4.dp
                    ),
//                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = message.decryptMessage ?: "",
                    color = MyBlack,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    Modifier
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ResponsiveText(
                        text = message.dateUpdate,
                        color = Color.DarkGray,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            ),
                        modifier = Modifier
                            .padding(
                                top = 0.dp,
                                start = 12.dp,
                                end = 12.dp,
                                bottom = 0.dp
                            )
                            .weight(1.5f),
                    )
                    Row(
                        horizontalArrangement = Arrangement.End, modifier = Modifier
                            .width(125.dp)
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (message.id != 0) {
                                    openDialog.value = true
                                    deleteText.value = context.getString(R.string.delete_one_note_message)
                                    messagesToDelete.value = mutableListOf(message)
                                }
                            }, modifier = Modifier.width(40.dp)
                        ) {
                            Icon(
                                modifier = Modifier.scale(0.65f),
                                painter = painterResource(id = R.drawable.trash_96),
                                contentDescription =  stringResource(R.string.delete),
                                tint = Color.DarkGray
                            )
                        }
                        IconButton(
                            onClick = {
                                if (message.decryptMessage!!.isNotBlank()) {
                                    clipboardManager.setText(AnnotatedString((message.decryptMessage)))
                                    if (toast != null) {
                                        toast?.cancel()
                                    }
                                    toast = Toast.makeText(context,  context.getString(R.string.copied), Toast.LENGTH_SHORT)
                                    toast?.show()
                                }
                            }, modifier = Modifier.width(40.dp)
                        ) {
                            Icon(
                                modifier = Modifier.scale(0.65f),
                                painter = painterResource(id = R.drawable.copy_96),
                                contentDescription =  stringResource(R.string.copy),
                                tint = Color.DarkGray
                            )
                        }
                        IconButton(
                            onClick = {
                                if (message.decryptMessage!!.isNotBlank()) {
                                    shareTextOrLink(context, message.decryptMessage)
                                }
                            }, modifier = Modifier.width(40.dp)
                        ) {
                            Icon(
                                modifier = Modifier.scale(0.65f),
                                painter = painterResource(id = R.drawable.share_96),
                                contentDescription =  stringResource(R.string.share),
                                tint = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}