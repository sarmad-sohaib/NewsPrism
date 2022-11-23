package com.sarmad.newsprism.data.paging.mediaters

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sarmad.newsprism.data.api.NewsApi
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.data.entities.ArticleRemoteKey
import com.sarmad.newsprism.data.localdatasource.ArticleDao
import com.sarmad.newsprism.data.localdatasource.ArticleDatabase
import com.sarmad.newsprism.data.localdatasource.RemoteKeysDao
import javax.inject.Inject
import kotlin.math.ceil

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val articleDatabase: ArticleDatabase,
    private val articleDao: ArticleDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val api: NewsApi
) : RemoteMediator<Int, Article>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage ?: return MediatorResult.Success(
                        remoteKey != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage =
                        remoteKey?.nextPage
                            ?: return MediatorResult.Success(remoteKey != null)
                    nextPage
                }
            }

            val response = api.getBreakingNews("us", currentPage)

            val totalPages = response.body()?.totalResults?.toDouble()?.div(20)?.let { pages ->
                ceil(pages)
            }?.toInt()

            val endOfPaginationReached = totalPages == currentPage

            val nextPage = if (endOfPaginationReached) null else currentPage.plus(1)
            val prevPage = if (currentPage == 1) null else currentPage.minus(1)

            articleDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    articleDao.deleteAllArticles()
                    remoteKeysDao.deleteAllArticleRemoteKeys()
                }

                response.body()?.let { response ->
                    val keys = articleDao.insertAll(response.articles)

                    val mappedKeysToArticles = keys.map { key ->
                        ArticleRemoteKey(
                            id = key.toInt(),
                            nextPage = nextPage,
                            prevPage = prevPage
                        )
                    }
                    remoteKeysDao.insertArticleRemoteKeys(mappedKeysToArticles)
                }
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (ex: java.lang.Exception) {
            return MediatorResult.Error(ex)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): ArticleRemoteKey? {
        return state.anchorPosition?.let { pos ->
            state.closestItemToPosition(pos)?.id?.let { id ->
                remoteKeysDao.getArticleRemoteKey(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Article>
    ): ArticleRemoteKey? {

        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull().let {
            it?.let { it1 -> remoteKeysDao.getArticleRemoteKey(it1.id) }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Article>
    ): ArticleRemoteKey? {

        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull().let {
            it?.let { it1 -> remoteKeysDao.getArticleRemoteKey(it1.id) }
        }
    }
}