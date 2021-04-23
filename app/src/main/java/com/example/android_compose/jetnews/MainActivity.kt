package com.example.android_compose.jetnews

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android_compose.jetnews.data.AppContainerImpl
import com.example.android_compose.jetnews.ui.JetnewsApp
import com.example.android_compose.jetnews.ui.NavigationViewModel

class MainActivity : AppCompatActivity() {

    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetnewsApp(AppContainerImpl(application), navigationViewModel)
        }
    }

    override fun onBackPressed() {
        if(!navigationViewModel.onBack()){
            super.onBackPressed()
        }
    }
}