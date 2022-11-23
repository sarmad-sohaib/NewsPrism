package com.sarmad.newsprism.data.localdatasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.utils.Constants.Companion.ARTICLES_TABLE_NAME

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>): List<Long>

    @Query("SELECT * FROM $ARTICLES_TABLE_NAME")
    fun getNewsStream(): PagingSource<Int, Article>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("DELETE FROM $ARTICLES_TABLE_NAME")
    suspend fun deleteAllArticles()

}