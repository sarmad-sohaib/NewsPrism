package com.sarmad.newsprism.data.repository

import androidx.paging.PagingData
import com.sarmad.newsprism.data.entities.NewsResponse
import kotlinx.coroutines.flow.Flow
import com.sarmad.newsprism.data.Result
import com.sarmad.newsprism.data.entities.Article

interface NewsRepository {


    suspend fun getSearchedNewsStream(searchQuery: String, pageNumber: Int):
            Flow<NewsResponse>

    suspend fun getBreakingNewsStream(countryCode: String): Flow<PagingData<Article>>
}