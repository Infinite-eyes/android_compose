package com.example.android_compose.jetnews.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.android_compose.jetnews.data.AppContainer
import com.example.android_compose.jetnews.data.interests.InterestsRepository
import com.example.android_compose.jetnews.data.posts.PostsRepository
import com.example.android_compose.jetnews.ui.article.ArticleScreen
import com.example.android_compose.jetnews.ui.home.HomeScreen
import com.example.android_compose.theme.JetnewsTheme

@Composable
fun JetnewsApp(
    appContainer: AppContainer,
    navigationViewModel: NavigationViewModel
) {
    JetnewsTheme {
        AppContent(
            navigationViewModel = navigationViewModel,
            interestsRepository =  appContainer.interestsRepository,
            postsRepository = appContainer.postsRepository
        )
    }
}

@Composable
private fun AppContent(
    navigationViewModel: NavigationViewModel,
    postsRepository: PostsRepository,
    interestsRepository: InterestsRepository
) {
    Crossfade(navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen(
                    navigateTo = navigationViewModel::navigateTo,
                    postsRepository = postsRepository,
                )
//                is Screen.Interests -> InterestsScreen(
//
//                )

                is Screen.Article -> ArticleScreen(
                    postId = screen.postId,
                    postsRepository = postsRepository,
                    onBack = {navigationViewModel.onBack()}
                )


            }
        }
    }
}