package com.example.android_compose.jetnews.data

import android.content.Context
import com.example.android_compose.jetnews.data.interests.InterestsRepository
import com.example.android_compose.jetnews.data.interests.impl.FakeInterestsRepository
import com.example.android_compose.jetnews.data.posts.PostsRepository
import com.example.android_compose.jetnews.data.posts.impl.FakePostsRepository

interface AppContainer{
    val postsRepository: PostsRepository
    val interestsRepository: InterestsRepository
}

class AppContainerImpl(private val applicationContext: Context
):AppContainer{
    override val postsRepository: PostsRepository by lazy{
        FakePostsRepository(
            resources = applicationContext.resources
        )
    }

    override val interestsRepository: InterestsRepository by lazy {
        FakeInterestsRepository()
    }

}