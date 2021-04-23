package com.example.android_compose.jetnews.data.posts.impl

import android.content.res.Resources
import com.example.android_compose.jetnews.data.Result
import com.example.android_compose.jetnews.data.posts.PostsRepository
import com.example.android_compose.jetnews.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import com.example.android_compose.jetnews.utils.addOrRemove

//@OptIn(ExperimentalCoroutinesApi::class)
class FakePostsRepository(
    private val resources: Resources
) : PostsRepository {


    private val favorites = MutableStateFlow<Set<String>>(setOf())
    private val mutex = Mutex()

    override suspend fun getPost(postId: String): Result<Post> {
        return withContext(Dispatchers.IO) {
            val post = posts.find { it.id == postId }
            if (post == null) {
                Result.Error(IllegalAccessException("Post not found"))
            } else {
                Result.Success(post)
            }
        }
    }

    override suspend fun getPosts(): Result<List<Post>> {
        return withContext(Dispatchers.IO) {
            delay(800)
            if(shouldRandomlyFail()){
                Result.Error(IllegalAccessException())
            }else{
                Result.Success(posts)
            }
        }
    }

    override fun observeFavorites(): Flow<Set<String>> = favorites

    override suspend fun toggleFavorite(postId: String) {
        mutex.withLock {
            val set = favorites.value.toMutableSet()
            set.addOrRemove(postId)
            favorites.value = set.toSet()
        }
    }

    private var requestCount = 0

    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0
}
