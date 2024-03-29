package com.example.android_compose.jetnews.ui

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.android_compose.jetnews.utils.getMutableStateOf


enum class ScreenName { HOME, INTERESTS, ARTICLE }

sealed class Screen(val id: ScreenName) {
    object Home : Screen(ScreenName.HOME)
    object Interests : Screen(ScreenName.INTERESTS)
    data class Article(val postId: String) : Screen(ScreenName.ARTICLE)
}

private const val SIS_SCREEN = "sis_screen"
private const val SIS_NAME = "screen_name"
private const val SIS_POST = "post"

private fun Screen.toBundle(): Bundle {
    return bundleOf(SIS_NAME to id.name).also {
        if(this is Screen.Article){
            it.putString(SIS_POST,postId)
        }
    }
}
private fun Bundle.toScreen(): Screen{
    val screenName = ScreenName.valueOf(getStringOrThrow(SIS_NAME))
    return when(screenName){
        ScreenName.HOME -> Screen.Home
        ScreenName.INTERESTS -> Screen.Interests
        ScreenName.ARTICLE -> {
            val postId = getStringOrThrow(SIS_POST)
            Screen.Article(postId)
        }
    }
}

private fun Bundle.getStringOrThrow(key:String)=
    requireNotNull(getString(key)){
        "Missing key '$key' in $this"
    }




class NavigationViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {


    var currentScreen : Screen by savedStateHandle.getMutableStateOf<Screen>(
        key = SIS_SCREEN,
        default = Screen.Home,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
        private set


    @MainThread
    fun onBack(): Boolean{
        val wasHandle = currentScreen != Screen.Home
        currentScreen = Screen.Home
        return wasHandle
    }


    @MainThread
    fun navigateTo(screen: Screen){
        currentScreen = screen
    }



}