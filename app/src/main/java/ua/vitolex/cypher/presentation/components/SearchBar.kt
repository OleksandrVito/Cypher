package ua.vitolex.cypher.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ua.vitolex.cypher.R
import ua.vitolex.cypher.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(query: MutableState<String>) {
    val kc = LocalSoftwareKeyboardController.current

    Column(Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 0.dp)) {
        OutlinedTextField(value = query.value,
            placeholder = { Text(text = stringResource(R.string.Search)) },
            maxLines = 1,
            onValueChange = { query.value = it },
            modifier = Modifier
                .clip(RoundedCornerShape(2.dp))
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MainColor,
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = MyOrange,
                backgroundColor = SecondaryGray,
                textColor = Color.Black,
                cursorColor = MyOrange,
                trailingIconColor = MainColor,
                focusedLabelColor = MainColor,
            ),
            singleLine = true,
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.value.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { query.value = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "clear search"
                        )

                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrect = true,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    kc?.hide()
                }
            ),
        )
    }
}