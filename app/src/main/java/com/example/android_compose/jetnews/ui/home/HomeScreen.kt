package com.example.android_compose.jetnews.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.android_compose.R
import com.example.android_compose.jetnews.data.posts.PostsRepository
import com.example.android_compose.jetnews.model.Post
import com.example.android_compose.jetnews.ui.AppDrawer
import com.example.android_compose.jetnews.ui.Screen
import com.example.android_compose.jetnews.ui.state.UiState
import com.example.android_compose.jetnews.utils.produceUiState
import kotlinx.coroutines.launch
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun HomeScreen(
    navigateTo: (Screen) -> Unit,
    postsRepository: PostsRepository,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val (postUiState, refreshPost, clearError) = produceUiState(postsRepository) {
        getPosts()
    }


    val favorites by postsRepository.observeFavorites().collectAsState(setOf())

    val coroutineScope = rememberCoroutineScope();



    HomeScreen(
        posts = postUiState.value,
        favorites = favorites,
        onToggleFavorite = {
            coroutineScope.launch { postsRepository.toggleFavorite(it) }
        },
        onRefreshPosts = refreshPost,
        onErrorDismiss = clearError,
        navigateTo = navigateTo,
        scaffoldState = scaffoldState
    )


}


@Composable
fun HomeScreen(
    posts: UiState<List<Post>>,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: () -> Unit,
    navigateTo: (Screen) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    if (posts.hasError) {
        val errorMessage = stringResource(id = R.string.load_error)
        val retryMessage = stringResource(id = R.string.retry)

        val onRefreshPostsState by rememberUpdatedState(onRefreshPosts)
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        LaunchedEffect(scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = retryMessage
            )
            when (snackbarResult) {
                SnackbarResult.ActionPerformed -> onRefreshPostsState()
                SnackbarResult.Dismissed -> onErrorDismissState()
            }
        }
    }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Home,
                closeDrawer = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                },
                navigateTo = navigateTo
            )
        },
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_jetnews_logo),
                            contentDescription = stringResource(R.string.cd_open_navigation_drawer)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            LoadingContent(
                empty = posts.initialLoad,
                emptyContent = { FullScreenLoading() },
                loading = posts.loading,
                onRefresh = onRefreshPosts,
                content = {
                    HomeScreenErrorAndContent(
                        posts = posts,
                        onRefresh = {
                            onRefreshPosts
                        },
                        navigateTo = navigateTo,
                        favorites = favorites,
                        onToggleFavorite = onToggleFavorite,
                        modifier = modifier
                    )
                }
            )
        }
    )
}

@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = { /*TODO*/ },
            content = content
        )
    }
}

@Composable
private fun HomeScreenErrorAndContent(
    posts: UiState<List<Post>>,
    onRefresh: () -> Unit,
    navigateTo: (Screen) -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (posts.data != null) {
        PostList(posts.data, navigateTo, favorites, onToggleFavorite, modifier)
    } else if (!posts.hasError) {
        TextButton(
            onClick = onRefresh, modifier.fillMaxSize()
        ) {
            Text("Tap to load content", textAlign = TextAlign.Center)
        }
    } else {
        Box(modifier.fillMaxSize())
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}


@Composable
private fun PostList(
    posts: List<Post>,
    navigateTo: (Screen) -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val postTop = posts[3]
    val postsSimple = posts.subList(0, 2)
    val postsPopular = posts.subList(2, 7)
    val postsHistory = posts.subList(7, 10)

    LazyColumn(modifier = modifier) {
        item { PostListTopSection(postTop, navigateTo) }
        item { PostListSimpleSection(postsSimple, navigateTo, favorites, onToggleFavorite) }
        item { PostListPopularSection(postsPopular, navigateTo) }
        item { PostListHistorySection(postsHistory, navigateTo) }
    }

}

@Composable
private fun PostListTopSection(post: Post, navigateTo: (Screen) -> Unit) {

    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = "Top stories for you",
        style = MaterialTheme.typography.subtitle1
    )

    PostCardTop(
        post = post,
        modifier = Modifier.clickable(onClick = {
            navigateTo(Screen.Article(post.id))
        })
    )

    PostListDivider()


}

@Composable
private fun PostListSimpleSection(
    posts: List<Post>,
    navigateTo: (Screen) -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit
) {
    Column {
        posts.forEach { post ->
            PostCardSimple(
                post = post,
                navigateTo = navigateTo,
                isFavorite = favorites.contains(post.id),
                onToggleFavorite = { onToggleFavorite(post.id) }
            )
            PostListDivider()
        }
    }
}

@Composable
private fun PostListPopularSection(
    posts: List<Post>,
    navigateTo: (Screen) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Popular on Jetnews",
            style = MaterialTheme.typography.subtitle1,
        )
        LazyRow(modifier = Modifier.padding(end = 16.dp)) {
            items(posts) { post ->
                PostCardPopular(post, navigateTo, Modifier.padding(start = 16.dp, bottom = 16.dp))
            }
        }
    }
}

@Composable
private fun PostListHistorySection(
    posts: List<Post>,
    navigateTo: (Screen) -> Unit
) {
    Column {
        posts.forEach { post ->
            PostCardHistory(post, navigateTo)
            PostListDivider()
        }
    }

}


@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08F)
    )
}











