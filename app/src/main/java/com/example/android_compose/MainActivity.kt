package com.example.android_compose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


//https://developer.android.google.cn/jetpack/compose/layout
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContent {
////           text
//            SimpleText()
//            StringResourceText()
//            BlueText()
//            BigText()
//            ItalicText()
//            CenterText()
//            DifferentFonts()
//            MultipleStylesInText()
//            LongText()
//            SelectableText()
//            SimpleClickableText()
//            AnnotatedClickableText()
//            SimpleFilledTextFieldSample()
//            SimpleOutlinedTextFieldSample()
//            StyledTextField()
            PasswordTextField()
////            ArtistCard();
//            AlignInRow()
        }
    }
}

