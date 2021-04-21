package com.example.android_compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


//@Composable
//fun ArtistCard() {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        Column {
////            Image( /*...*/ )
//            Text("Alfred Sisley")
//            Text("3 minutes ago")
//        }
//    }
//}




@Composable
fun AlignInRow() {
    Row(
        modifier = Modifier.size(150.dp).background(Color.Yellow),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(Modifier.size(50.dp).background(Color.Red))
        Box(Modifier.size(50.dp).background(Color.Blue))
    }
}