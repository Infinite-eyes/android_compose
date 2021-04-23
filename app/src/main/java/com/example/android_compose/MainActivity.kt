package com.example.android_compose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.android_compose.ui.DecoupledConstraintLayout


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
//            PasswordTextField()


 ////layout
//            ArtistCard();
//            AlignInRow()
//            PaddedComposable()
//            SizedComposable()
//            FixedSizeComposable()
//            FillSizeComposable()
//            MatchParentSizeComposable()
//            TextWithPaddingFromBaseline()
//            OffsetComposable()
//            FlexibleComposable()
//            WithConstraintsComposable()
//            ConstraintsLayoutContent()
            DecoupledConstraintLayout()
        }
    }
}

