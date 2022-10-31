package com.sarmad.newsprism.data.repository

import com.sarmad.newsprism.data.entities.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getBreakingNewsStream(countryCode: String, pageNumber: Int):
            Flow<NewsResponse>

    suspend fun getSearchedNewsStream(searchQuery: String, pageNumber: Int):
            Flow<NewsResponse>
}