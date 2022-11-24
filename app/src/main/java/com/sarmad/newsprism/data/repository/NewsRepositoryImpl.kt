package com.sarmad.newsprism.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sarmad.newsprism.data.remotedatasource.api.NewsApi
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.data.entities.NewsResponse
import com.sarmad.newsprism.data.localdatasource.ArticleDao
import com.sarmad.newsprism.data.localdatasource.ArticleDatabase
import com.sarmad.newsprism.data.localdatasource.RemoteKeysDao
import com.sarmad.newsprism.data.paging.mediaters.NewsRemoteMediator
import com.sarmad.newsprism.utils.Constants.Companion.PAGING_CONFIG_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "NewsRepositoryImpl"

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val articleDao: ArticleDao,
    private val articleDatabase: ArticleDatabase,
    private val remoteKeysDao: RemoteKeysDao
) : NewsRepository {

    override suspend fun getSearchedNewsStream(
        searchQuery: String,
        pageNumber: Int
    ): Flow<NewsResponse> = flow {
        val searchedNewsResponse = api.searchNews(searchQuery, pageNumber)

        if (searchedNewsResponse.isSuccessful) searchedNewsResponse.body()
            ?.let { newsList -> emit(newsList) }
        else emptyFlow<NewsResponse>()
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getBreakingNewsStream(
        countryCode: String
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_CONFIG_PAGE_SIZE
            ),
            remoteMediator = NewsRemoteMediator(articleDatabase, articleDao, remoteKeysDao, api),
            pagingSourceFactory = { articleDao.getNewsStream() }
        ).flow
    }
}