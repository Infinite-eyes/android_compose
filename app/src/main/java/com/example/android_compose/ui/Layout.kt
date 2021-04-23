package com.example.android_compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

//@Composable
//fun ArtistCard() {
//    Column {
//        Text("Alfred Sisley")
//        Text("3 minutes ago")
//    }
//}

//@Composable
//fun ArtistCard() {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        Column {
////              Image( /*...*/ )
//            Text("Alfred Sisley")
//            Text("3 minutes ago")
//        }
//    }
//}

@Composable
fun ArtistCard() {
    Column(
        modifier = Modifier
            .clickable { }
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {}
        Spacer(Modifier.size(20.dp))
        Card(elevation = 4.dp) {}
    }
}


@Composable
fun AlignInRow() {
    Row(
        modifier = Modifier
            .size(150.dp)
            .background(Color.Yellow),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(50.dp)
                .background(Color.Red)
        )
        Box(
            Modifier
                .size(50.dp)
                .background(Color.Blue)
        )
    }
}

@Composable
fun PaddedComposable() {
    Text(
        "Hello World", modifier = Modifier
            .background(Color.Green)
            .padding(20.dp)
    )
}

@Composable
fun SizedComposable() {
    Box(
        Modifier
            .size(100.dp, 100.dp)
            .background(Color.Red)
    )
}

@Composable
fun FixedSizeComposable() {
    Box(
        Modifier
            .size(90.dp, 150.dp)
            .background(Color.Green)
    ) {
        Box(
            Modifier
                .requiredSize(100.dp, 100.dp)
                .background(Color.Red)
        )
    }
}

@Composable
fun FillSizeComposable() {
    Box(
        Modifier
            .background(Color.Green)
            .size(50.dp)
            .padding(10.dp)
    ) {
        Box(
            Modifier
                .background(Color.Blue)
                .fillMaxSize()
        )
    }
}

@Composable
fun MatchParentSizeComposable() {
    Box {
        Spacer(
            Modifier
                .fillMaxSize()
                .background(Color.Green)
        )
        Text("Hello World")
    }
}

@Composable
fun TextWithPaddingFromBaseline() {
    Box(Modifier.background(Color.Yellow)) {
        Text("Hi there!", Modifier.paddingFromBaseline(top = 32.dp))
    }
}


@Composable
fun OffsetComposable() {
    Box(
        Modifier
            .background(Color.Yellow)
            .size(width = 150.dp, height = 70.dp)
    ) {
        Text("Layout offset modifier sample", Modifier.offset(x = 15.dp, y = 20.dp))
    }
}

@Composable
fun FlexibleComposable() {
    Row(Modifier.width(210.dp)) {
        Box(
            Modifier
                .weight(2f)
                .height(50.dp)
                .background(Color.Blue)
        )
        Box(
            Modifier
                .weight(1f)
                .height(50.dp)
                .background(Color.Red)
        )
    }
}

@Composable
fun WithConstraintsComposable() {
    BoxWithConstraints {
        Text("My minHeight is  $minHeight while my maxWidth is $maxWidth")
    }
}

@Composable
fun ConstraintsLayoutContent() {

    ConstraintLayout {

        val (button, text) = createRefs()

        Button(onClick = {},
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }


        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
        })
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints() {
        val constraints = if (minWidth < 600.dp) {
            decoupledConstraints(margin = 16.dp)
        }else{
            decoupledConstraints(margin = 32.dp)
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = {},
                modifier = Modifier.layoutId("button")
            ){
                Text("Button")
            }

            Text("Text",Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button){
            top.linkTo(parent.top,margin = margin)
        }

        constrain(button){
            top.linkTo(parent.bottom,margin = margin)
        }
    }
}














