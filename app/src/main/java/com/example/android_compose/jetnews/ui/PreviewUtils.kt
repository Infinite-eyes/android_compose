package com.example.android_compose.jetnews.ui

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.android_compose.theme.JetnewsTheme

@Composable
internal fun ThemedPreview(
    darkTheme:Boolean = false,
    content: @Composable ()-> Unit
){
    JetnewsTheme(darkTheme = darkTheme) {
        Surface {
            content()
        }
    }
}