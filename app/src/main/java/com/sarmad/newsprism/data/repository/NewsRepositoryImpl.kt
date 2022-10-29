package com.sarmad.newsprism.data.repository

import com.sarmad.newsprism.data.api.RetrofitInstance
import com.sarmad.newsprism.data.entities.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor() : NewsRepository {
    override suspend fun getBreakingNewsStream(
        countryCode: String,
        pageNumber: Int
    ): Flow<NewsResponse> = flow {
        val newsResponse = RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

        if (newsResponse.isSuccessful) newsResponse.body()?.let { emit(it) }
        else emptyFlow<NewsResponse>()
    }

    override suspend fun getSearchedNewsStream(
        countryCode: String,
        pageNumber: Int
    ): Flow<NewsResponse> = flow {
        val searchedNewsResponse = RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

        if (searchedNewsResponse.isSuccessful) searchedNewsResponse.body()?.let { emit(it) }
        else emptyFlow<NewsResponse>()
    }
}