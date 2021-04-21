package com.example.android_compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//https://developer.android.google.cn/jetpack/compose/text

@Composable
fun SimpleText() {
    Text("Hello World")
}

@Composable //资源字体
fun StringResourceText() {
    Text(stringResource(R.string.app_name))
}

@Composable //字体颜色
fun BlueText() {
    Text("Hello World", color = Color.Blue)
}

@Composable //字体大小
fun BigText() {
    Text("Hello World", fontSize = 30.sp)
}


@Composable  //斜体
fun ItalicText() {
    Text("Hello World", fontStyle = FontStyle.Italic)
}

@Preview(showBackground = true)
@Composable
fun CenterText() {
    Text(
        "Hello World", textAlign = TextAlign.Center,
        modifier = Modifier.width(350.dp)
    )
}

val firaSansFamily = FontFamily(
    Font(R.font.domine_bold, FontWeight.Light),
    Font(R.font.domine_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.montserrat_regular, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.Bold)
)

@Composable
fun DifferentFonts() {
    Column {
//        Text("Hello World", fontFamily = FontFamily.Serif)
//        Text("Hello World", fontFamily = FontFamily.SansSerif)
//        Text("Hello World", fontFamily = FontFamily.SansSerif)
        Text("Hello World", fontFamily = firaSansFamily, fontWeight = FontWeight.Light)
        Text("Hello World", fontFamily = firaSansFamily, fontWeight = FontWeight.Normal)
        Text(
            "Hello World",
            fontFamily = firaSansFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic
        )
        Text("Hello World", fontFamily = firaSansFamily, fontWeight = FontWeight.Medium)
        Text("Hello World", fontFamily = firaSansFamily, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MultipleStylesInText() {
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Blue)) {
            append("H")
        }
        append("ello ")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
            append("W")
        }
        append("orld")
    })
}

@Composable
fun LongText() {
//    Text("hello ".repeat(50),maxLines = 2)
    Text("Hello Compose ".repeat(50), maxLines = 2, overflow = TextOverflow.Ellipsis)
}

@Composable
fun SelectableText() {
    SelectionContainer {
        Text("This text is selectable")
    }
}

@Composable
fun PartiallySelectableText() {
    SelectionContainer {
        Column {
            Text("This text is selectable")
            Text("This one too")
            Text("This one as well")
            DisableSelection {
                Text("But not this one")
                Text("Neither this one")
            }
            Text("But again, you can select this one")
            Text("And this one too")
        }
    }
}


@Composable
fun SimpleClickableText() {
    ClickableText(
        text = AnnotatedString("Click Me"),
        onClick = { offset ->
            Log.d("ClickableText", "$offset -th character is clicked.")
        })
}

@Composable
fun AnnotatedClickableText() {
    val annotatedText = buildAnnotatedString {
        append("Click ")

        pushStringAnnotation(
            tag = "URL",
            annotation = "https://developer.android.com"
        )
        withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
            append("here")
        }
        pop()
    }

    ClickableText(text = annotatedText, onClick = { offset ->
        annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset).firstOrNull()
            ?.let { annotation ->
                Log.d("Clicked URL", annotation.item)
            }
    })
}

@Composable
fun SimpleFilledTextFieldSample() {
//    var text by remember { mutableStateOf(TextFieldValue(text = "Hello")) }
    var text by remember { mutableStateOf("Hello") }

    TextField(
        value = text,
        onValueChange = {
            Log.e("TextFieldDemo", "输入内容更新：$it")
            text = it
//            text = TextFieldValue(text = it)
        },
        label = {
            Text("Label")
        }
    )
}

@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by remember { mutableStateOf("Hello") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
        },
        label = {
            Text("Label")
        }
    )
}

@Composable
fun StyledTextField() {
    var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }

    TextField(
        value = value,
        onValueChange = { value = it },
        label = { Text("Enter text") },
        maxLines = 2,
        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password, onValueChange = { password = it },
        label = { Text("Enter password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}









