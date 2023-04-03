package ua.vitolex.cypher.presentation.screens.main


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ua.vitolex.cypher.R
import ua.vitolex.cypher.presentation.components.CalcButtonComponent
import ua.vitolex.cypher.presentation.components.ResponsiveText
import ua.vitolex.cypher.presentation.navigation.Screens
import ua.vitolex.cypher.presentation.screens.MainViewModel
import ua.vitolex.cypher.ui.theme.*
import ua.vitolex.cypher.utils.rememberWindowInfo
import ua.vitolex.cypher.utils.WindowInfo
import ua.vitolex.cypher.utils.shadow


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    var inputValue by rememberSaveable {
        mutableStateOf("")
    }
    var outputValue by rememberSaveable {
        mutableStateOf("")
    }

    var key by rememberSaveable {
        mutableStateOf("")
    }
    var showPassword by remember { mutableStateOf(false) }

    var switchState by rememberSaveable {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

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

    val windowInfo = rememberWindowInfo()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp)
                    ) {
                        Text(
                            text = "Cypher",
                            style = MaterialTheme.typography.h1,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 32.dp, top = 0.dp, bottom = 0.dp, end = 0.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                actions = {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier
                        .size(width = 70.dp, height = 50.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(
                                modifier = Modifier.scale(0.9f),
                                onClick = {
                                    navController.navigate(Screens.HistoryScreen.rout)
                                }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_history_24),
                                    contentDescription = stringResource(R.string.history),
                                    tint = MyOrange,
                                    modifier = Modifier.scale(1.5f)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                    }
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
            if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                Column(
                    modifier = Modifier
                        .background(MyGray)
                        .padding(top = 4.dp, start = 12.dp, end = 12.dp, bottom = 0.dp)
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8f)
                            .shadow(
                                color = Color(0x0DFFFFFF),
                                offsetX = (-6).dp,
                                offsetY = (-6).dp,
                                blurRadius = 15.dp
                            )
                            .shadow(
                                color = Color(0x4D000000),
                                offsetX = (6).dp,
                                offsetY = (6).dp,
                                blurRadius = 15.dp

                            )
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color(0xAA2E373F))
                        ) {
                        OutlinedTextField(
                            value = outputValue,
                            onValueChange = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(Color(0xAA2E373F))
                            ,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                disabledBorderColor = Color.Transparent,
                                errorBorderColor = Color.Transparent,
                                textColor= MyWhite
                            ),
                            readOnly = true
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(2f)) {
                            ResponsiveText(
                                modifier = Modifier
                                    .padding(bottom = 2.dp),
                                text = stringResource(R.string.save_in_history),
                                color = MainColor,
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    textAlign= TextAlign.Center
                                ),
                            )
                            Switch(
                                checked = switchState,
                                onCheckedChange = { switchState = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.DarkGray,
                                    uncheckedThumbColor = Color.DarkGray,
                                    checkedTrackColor = MyOrange,
                                    uncheckedTrackColor = MainColor,
                                )
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.End, modifier = Modifier
                                .width(104.dp)
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    if (outputValue.isNotBlank()) {
                                        clipboardManager.setText(AnnotatedString((outputValue)))
                                        if (toast != null) {
                                            toast?.cancel()
                                        }
                                        toast = Toast.makeText(
                                            context,
                                            context.getString(R.string.copied),
                                            Toast.LENGTH_SHORT
                                        )
                                        toast?.show()
                                    }
                                }, modifier = Modifier.width(40.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.scale(0.65f),
                                    painter = painterResource(id = R.drawable.copy_96),
                                    contentDescription = stringResource(R.string.copy),
                                    tint = Color.DarkGray
                                )
                            }
                            IconButton(
                                onClick = {
                                    if (outputValue.isNotBlank()) {
                                        shareTextOrLink(context, outputValue)
                                    }
                                }, modifier = Modifier.width(40.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.scale(0.65f),
                                    painter = painterResource(id = R.drawable.share_96),
                                    contentDescription = stringResource(R.string.share),
                                    tint = Color.DarkGray
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = key,
                        onValueChange = { key = it },
                        placeholder = { Text(text = stringResource(id = R.string.enter_key)) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = MainColor,
                            disabledBorderColor = Color.Transparent,
                            focusedBorderColor = MyOrange.copy(0.6f),
                            backgroundColor = SecondaryGray,
                            textColor = MyBlack,
                            cursorColor = MyOrange.copy(0.6f),
                            trailingIconColor = MainColor,
                            focusedLabelColor = MainColor,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(2.dp)),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            autoCorrect = true,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        visualTransformation = if (showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        }, trailingIcon = {
                            if (showPassword) {
                                IconButton(onClick = { showPassword = false }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_visibility_24),
                                        contentDescription = stringResource(R.string.visibility)
                                    )
                                }
                            } else {
                                IconButton(onClick = { showPassword = true }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24),
                                        contentDescription = stringResource(R.string.visibility_off)
                                    )
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = inputValue,
                        onValueChange = { inputValue = it },
                        placeholder = { Text(text = stringResource(id = R.string.enter_your_text)) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = MainColor,
                            disabledBorderColor = Color.Transparent,
                            focusedBorderColor = MyOrange.copy(0.6f),
                            backgroundColor = SecondaryGray,
                            textColor = MyBlack,
                            cursorColor = MyOrange.copy(0.6f),
                            trailingIconColor = MainColor,
                            focusedLabelColor = MainColor,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .weight(1f)
                            .clip(shape = RoundedCornerShape(2.dp)),
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = inputValue.isNotEmpty(),
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxHeight(),
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    IconButton(onClick = {
                                        inputValue = ""
                                        outputValue = ""
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = stringResource(R.string.clean_search)
                                        )

                                    }
                                }

                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            autoCorrect = true,
                        ),
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CalcButtonComponent(
                            color = MyOrange.copy(0.6f),
                            text = stringResource(R.string.encrypt),
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(50.dp)
                        ) {
                            if (key.isEmpty()) {
                                if (toast != null) {
                                    toast?.cancel()
                                }
                                toast = Toast.makeText(
                                    context,
                                    context.getString(R.string.you_need_key),
                                    Toast.LENGTH_SHORT
                                )
                                toast?.show()
                            } else {
                                if (inputValue.isNotEmpty()) {
                                    outputValue = viewModel.encryptMessage(
                                        inputValue, key
                                    )
                                    if (switchState) viewModel.createMessage(
                                        inputValue,
                                        outputValue
                                    )
                                } else {
                                    if (toast != null) {
                                        toast?.cancel()
                                    }
                                    toast = Toast.makeText(
                                        context,
                                        context.getString(R.string.enter_text),
                                        Toast.LENGTH_SHORT
                                    )
                                    toast?.show()
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        CalcButtonComponent(
                            color = Color.Gray,
                            text = stringResource(R.string.decrypt),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            if (key.isEmpty()) {
                                if (toast != null) {
                                    toast?.cancel()
                                }
                                toast = Toast.makeText(
                                    context,
                                    context.getString(R.string.you_need_key),
                                    Toast.LENGTH_SHORT
                                )
                                toast?.show()
                            } else {
                                if (inputValue.isNotEmpty()) {
                                    outputValue = viewModel.decryptMessage(
                                        inputValue, key
                                    )
                                } else {
                                    if (toast != null) {
                                        toast?.cancel()
                                    }
                                    toast = Toast.makeText(
                                        context,
                                        context.getString(R.string.enter_text),
                                        Toast.LENGTH_SHORT
                                    )
                                    toast?.show()
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .background(MyGray)
                        .padding(top = 4.dp, start = 12.dp, end = 12.dp, bottom = 0.dp)
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {

                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 150.dp)
                                .weight(0.8f)
                                .shadow(
                                    color = Color(0x0DFFFFFF),
                                    offsetX = (-6).dp,
                                    offsetY = (-6).dp,
                                    blurRadius = 15.dp
                                )
                                .shadow(
                                    color = Color(0x4D000000),
                                    offsetX = (6).dp,
                                    offsetY = (6).dp,
                                    blurRadius = 15.dp

                                )
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(0xAA2E373F))
                        ) {
                            OutlinedTextField(
                                value = outputValue,
                                onValueChange = { },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .heightIn(min = 150.dp)
                                    .background(Color(0xAA2E373F)),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    disabledBorderColor = Color.Transparent,
                                    errorBorderColor = Color.Transparent,
                                    textColor = MyWhite
                                ),
                                readOnly = true
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .weight(2f)
                            ) {
                                ResponsiveText(
                                    modifier = Modifier
                                        .padding(bottom = 2.dp),
                                    text = stringResource(R.string.save_in_history),
                                    color = MainColor,
                                    textStyle = TextStyle(
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                )
                                Switch(
                                    checked = switchState,
                                    onCheckedChange = { switchState = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.DarkGray,
                                        uncheckedThumbColor = Color.DarkGray,
                                        checkedTrackColor = MyOrange,
                                        uncheckedTrackColor = MainColor,
                                    )
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.End, modifier = Modifier
                                    .width(104.dp)
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        if (outputValue.isNotBlank()) {
                                            clipboardManager.setText(AnnotatedString((outputValue)))
                                            if (toast != null) {
                                                toast?.cancel()
                                            }
                                            toast = Toast.makeText(
                                                context,
                                                context.getString(R.string.copied),
                                                Toast.LENGTH_SHORT
                                            )
                                            toast?.show()
                                        }
                                    }, modifier = Modifier.width(40.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier.scale(0.65f),
                                        painter = painterResource(id = R.drawable.copy_96),
                                        contentDescription = stringResource(R.string.copy),
                                        tint = Color.DarkGray
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        if (outputValue.isNotBlank()) {
                                            shareTextOrLink(context, outputValue)
                                        }
                                    }, modifier = Modifier.width(40.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier.scale(0.65f),
                                        painter = painterResource(id = R.drawable.share_96),
                                        contentDescription = stringResource(R.string.share),
                                        tint = Color.DarkGray
                                    )
                                }
                            }

                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = key,
                            onValueChange = { key = it },
                            placeholder = { Text(text = stringResource(id = R.string.enter_key)) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = MainColor,
                                disabledBorderColor = Color.Transparent,
                                focusedBorderColor = MyOrange.copy(0.6f),
                                backgroundColor = SecondaryGray,
                                textColor = MyBlack,
                                cursorColor = MyOrange.copy(0.6f),
                                trailingIconColor = MainColor,
                                focusedLabelColor = MainColor,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(2.dp)),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                autoCorrect = true,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            visualTransformation = if (showPassword) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            }, trailingIcon = {
                                if (showPassword) {
                                    IconButton(onClick = { showPassword = false }) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_visibility_24),
                                            contentDescription = stringResource(R.string.visibility)
                                        )
                                    }
                                } else {
                                    IconButton(onClick = { showPassword = true }) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24),
                                            contentDescription = stringResource(R.string.visibility_off)
                                        )
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = inputValue,
                            onValueChange = { inputValue = it },
                            placeholder = { Text(text = stringResource(id = R.string.enter_your_text)) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = MainColor,
                                disabledBorderColor = Color.Transparent,
                                focusedBorderColor = MyOrange.copy(0.6f),
                                backgroundColor = SecondaryGray,
                                textColor = MyBlack,
                                cursorColor = MyOrange.copy(0.6f),
                                trailingIconColor = MainColor,
                                focusedLabelColor = MainColor,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f)
                                .heightIn(min = 150.dp)
                                .weight(1f)
                                .clip(shape = RoundedCornerShape(2.dp)),
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = inputValue.isNotEmpty(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxHeight(),
                                        contentAlignment = Alignment.TopCenter
                                    ) {
                                        IconButton(onClick = {
                                            inputValue = ""
                                            outputValue = ""
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = stringResource(R.string.clean_search)
                                            )

                                        }
                                    }

                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                autoCorrect = true,
                            ),
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            CalcButtonComponent(
                                color = MyOrange.copy(0.6f),
                                text = stringResource(R.string.encrypt),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(50.dp)
                            ) {
                                if (key.isEmpty()) {
                                    if (toast != null) {
                                        toast?.cancel()
                                    }
                                    toast = Toast.makeText(
                                        context,
                                        context.getString(R.string.you_need_key),
                                        Toast.LENGTH_SHORT
                                    )
                                    toast?.show()
                                } else {
                                    if (inputValue.isNotEmpty()) {
                                        outputValue = viewModel.encryptMessage(
                                            inputValue, key
                                        )
                                        if (switchState) viewModel.createMessage(
                                            inputValue,
                                            outputValue
                                        )
                                    } else {
                                        if (toast != null) {
                                            toast?.cancel()
                                        }
                                        toast = Toast.makeText(
                                            context,
                                            context.getString(R.string.enter_text),
                                            Toast.LENGTH_SHORT
                                        )
                                        toast?.show()
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            CalcButtonComponent(
                                color = Color.Gray,
                                text = stringResource(R.string.decrypt),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                if (key.isEmpty()) {
                                    if (toast != null) {
                                        toast?.cancel()
                                    }
                                    toast = Toast.makeText(
                                        context,
                                        context.getString(R.string.you_need_key),
                                        Toast.LENGTH_SHORT
                                    )
                                    toast?.show()
                                } else {
                                    if (inputValue.isNotEmpty()) {
                                        outputValue = viewModel.decryptMessage(
                                            inputValue, key
                                        )
                                    } else {
                                        if (toast != null) {
                                            toast?.cancel()
                                        }
                                        toast = Toast.makeText(
                                            context,
                                            context.getString(R.string.enter_text),
                                            Toast.LENGTH_SHORT
                                        )
                                        toast?.show()
                                    }
                                }
                            }
                        }
                    }
                }
                    Spacer(modifier = Modifier.padding(4.dp))
            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(60.dp)
//                    .background(Color.Red)
//            )
        }
    }
}

@Composable
fun Int.scaledSp(): TextUnit {
    val value: Int = this
    return with(LocalDensity.current) {
        val fontScale = this.fontScale
        val textSize = value / fontScale
        textSize.sp
    }
}