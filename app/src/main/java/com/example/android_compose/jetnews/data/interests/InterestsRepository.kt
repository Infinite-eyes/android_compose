package com.example.android_compose.jetnews.data.interests

import com.example.android_compose.jetnews.data.Result
import kotlinx.coroutines.flow.Flow

typealias TopicsMap = Map<String, List<String>>

interface InterestsRepository {

    suspend fun getTopics(): Result<TopicsMap>

    suspend fun getPeople(): Result<List<String>>

    suspend fun getPublications(): Result<List<String>>

    suspend fun toggleTopicSelection(topic: TopicSelection)

    suspend fun togglePersonSelected(person: String)

    suspend fun togglePublicationSelected(publication: String)

    fun observeTopicsSelected(): Flow<Set<TopicSelection>>

    fun observePeopleSelected(): Flow<Set<String>>

    fun observePublicationSelected(): Flow<Set<String>>

}

data class TopicSelection(val section:String, val topic:String)








