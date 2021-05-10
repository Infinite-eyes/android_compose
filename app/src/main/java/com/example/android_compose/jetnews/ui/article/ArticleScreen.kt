package com.example.android_compose.jetnews.ui.article

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.android_compose.R
import com.example.android_compose.jetnews.data.posts.PostsRepository
import com.example.android_compose.jetnews.model.Post
import com.example.android_compose.jetnews.utils.produceUiState
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_compose.jetnews.data.posts.impl.BlockingFakePostsRepository
import com.example.android_compose.jetnews.ui.ThemedPreview
import com.example.android_compose.jetnews.ui.home.BookmarkButton
import kotlinx.coroutines.runBlocking
import com.example.android_compose.jetnews.data.Result
import com.example.android_compose.jetnews.data.posts.impl.post3
import com.example.android_compose.jetnews.data.posts.impl.posts
import kotlinx.coroutines.launch

@Composable
fun ArticleScreen(
    postId: String,
    postsRepository: PostsRepository,
    onBack: () -> Unit
) {
    val (post) = produceUiState(postsRepository, postId) {
        getPost(postId)
    }

    val postData = post.value.data ?: return

    val favorites by postsRepository.observeFavorites().collectAsState(setOf())
    val isFavorite = favorites.contains(postId)

    val coroutineScope = rememberCoroutineScope()

    ArticleScreen(
        post = postData,
        onBack = onBack,
        isFavorite = isFavorite,
        onToggleFavorite = {
            coroutineScope.launch {
                postsRepository.toggleFavorite(postId)
            }
        }
    )


}

@Composable
fun ArticleScreen(
    post: Post,
    onBack: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    if (showDialog) {
        FunctionalityNotAvailablePopup { showDialog = false }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Published in: ${post.publication?.name}",
                        style = MaterialTheme.typography.subtitle2,
                        color = LocalContentColor.current
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.cd_navigate_up
                            )
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            PostContent(post, modifier)
        },
        bottomBar = {
            BottomBar(
                post = post,
                onUnimplementedAction = { showDialog = true },
                isFavorite = isFavorite,
                onToggleFavorite = onToggleFavorite
            )
        }
    )
}


@Composable
private fun BottomBar(
    post: Post,
    onUnimplementedAction: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Surface(elevation = 2.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = onUnimplementedAction) {
                Icon(
                    imageVector = Icons.Filled.ThumbUpOffAlt,
                    contentDescription = stringResource(R.string.cd_add_to_favorites)
                )
            }
            BookmarkButton(
                isBookmarked = isFavorite,
                onClick = onToggleFavorite
            )

            val context = LocalContext.current

            IconButton(onClick = { sharePost(post, context) }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = stringResource(R.string.cd_share)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onUnimplementedAction) {
                Icon(
                    painter = painterResource(R.drawable.ic_text_settings),
                    contentDescription = stringResource(R.string.cd_text_settings)
                )
            }


        }
    }
}


@Composable
private fun FunctionalityNotAvailablePopup(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = "Functionality not available \uD83D\uDE48",
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "CLOSE")
            }
        }
    )
}

private fun sharePost(post: Post, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, post.title)
        putExtra(Intent.EXTRA_TEXT, post.url)
    }
    context.startActivity(Intent.createChooser(intent, "Share post"))
}

@Preview("Article screen")
@Composable
fun PreviewArticle() {
    ThemedPreview {
        val post = loadFakePost(post3.id)
        ArticleScreen(post,{},false,{})
    }
}


@Preview("Article screen")
@Composable
fun PreviewArticleDark() {
    ThemedPreview(darkTheme = true) {
        val post = loadFakePost(post3.id)
        ArticleScreen(post,{},false,{})
    }
}


@Composable
private fun loadFakePost(postId: String): Post {
    val context = LocalContext.current
    val post  = runBlocking {
        (BlockingFakePostsRepository(context).getPost(postId) as Result.Success).data
    }
    return post
}
















