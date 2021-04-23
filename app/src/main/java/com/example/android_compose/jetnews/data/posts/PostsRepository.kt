package com.example.android_compose.jetnews.data.posts

import com.example.android_compose.jetnews.model.Post
import com.example.android_compose.jetnews.data.Result
import kotlinx.coroutines.flow.Flow

interface PostsRepository{

    suspend fun getPost(postId:String) : Result<Post>

    suspend fun getPosts(): Result<List<Post>>

    fun observeFavorites(): Flow<Set<String>>

    suspend fun toggleFavorite(postId: String)
}