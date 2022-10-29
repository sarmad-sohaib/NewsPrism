package com.sarmad.newsprism.data.localdatasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarmad.newsprism.data.entities.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    suspend fun getNewsStream(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}