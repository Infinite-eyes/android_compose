package com.example.android_compose.jetnews.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_compose.jetnews.data.posts.impl.post1
import com.example.android_compose.jetnews.model.Post
import com.example.android_compose.jetnews.model.PostAuthor
import com.example.android_compose.jetnews.ui.Screen
import com.example.android_compose.jetnews.ui.ThemedPreview

@Composable
fun PostCardPopular(
    post: Post,
    navigateTo: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.size(280.dp, 240.dp)
    ) {
        Column(modifier = Modifier.clickable(onClick = {
            navigateTo(Screen.Article(post.id))
        })) {
            Image(
                painter = painterResource(post.imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = post.metadata.author.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = "${post.metadata.date} -" +
                            "${post.metadata.readTimeMinutes} min read",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Preview("Regular colors")
@Composable
fun PreviewPostCardPopular() {
    ThemedPreview {
        PostCardPopular(post1, {})
    }
}


@Preview("Regular colors")
@Composable
fun PreviewPostCardPopularDark() {
    ThemedPreview(darkTheme = true) {
        PostCardPopular(post1, {})
    }
}

@Preview("Regular colors, long text")
@Composable
fun PreviewPostCardPopularLongText() {

    val loremIpsum =
        """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ullamcorper pharetra massa,
        sed suscipit nunc mollis in. Sed tincidunt orci lacus, vel ullamcorper nibh congue quis.
        Etiam imperdiet facilisis ligula id facilisis. Suspendisse potenti. Cras vehicula neque sed
        nulla auctor scelerisque. Vestibulum at congue risus, vel aliquet eros. In arcu mauris,
        facilisis eget magna quis, rhoncus volutpat mi. Phasellus vel sollicitudin quam, eu
        consectetur dolor. Proin lobortis venenatis sem, in vestibulum est. Duis ac nibh interdum,
        """.trimIndent()


    ThemedPreview {
        PostCardPopular(
            post1.copy(
                title = "Title$loremIpsum",
                metadata = post1.metadata.copy(
                    author = PostAuthor("Author: $loremIpsum"),
                    readTimeMinutes = Int.MAX_VALUE
                )
            ),
            {}
        )
    }


}





























