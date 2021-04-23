package com.example.android_compose.jetnews.data.posts.impl

import android.content.Context
import com.example.android_compose.jetnews.data.Result
import com.example.android_compose.jetnews.data.posts.PostsRepository
import com.example.android_compose.jetnews.model.Post
import com.example.android_compose.jetnews.utils.addOrRemove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class BlockingFakePostsRepository(private val context: Context) : PostsRepository {

    private val favorites = MutableStateFlow<Set<String>>(setOf())

    override suspend fun getPost(postId: String): Result<Post> {
        return withContext(Dispatchers.IO) {
            val post = posts.find { it.id == postId }
            if (post == null) {
                Result.Error(IllegalAccessException("Unable to find post"))
            } else {
                Result.Success(post)
            }
        }
    }

    override suspend fun getPosts(): Result<List<Post>> {
        return Result.Success(posts)
    }

    override fun observeFavorites(): Flow<Set<String>> = favorites

    override suspend fun toggleFavorite(postId: String) {
        val set = favorites.value.toMutableSet()
        set.addOrRemove(postId)
        favorites.value = set
    }


}