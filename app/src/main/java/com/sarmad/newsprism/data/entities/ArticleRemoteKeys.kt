package com.sarmad.newsprism.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarmad.newsprism.utils.Constants.Companion.REMOTE_KEYS_TABLE_NAME

@Entity(
    tableName = REMOTE_KEYS_TABLE_NAME
)
data class ArticleRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val nextPage: Int?,
    val prevPage: Int?,
    val modifiedAt: Long?
        )
