package com.sarmad.newsprism.data.localdatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarmad.newsprism.data.entities.ArticleRemoteKey
import com.sarmad.newsprism.utils.Constants.Companion.REMOTE_KEYS_TABLE_NAME

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM $REMOTE_KEYS_TABLE_NAME WHERE id = :id")
    suspend fun getArticleRemoteKey(id: Int): ArticleRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleRemoteKeys(remoteKey: List<ArticleRemoteKey?>?)

    @Query("DELETE FROM $REMOTE_KEYS_TABLE_NAME")
    suspend fun deleteAllArticleRemoteKeys()
}