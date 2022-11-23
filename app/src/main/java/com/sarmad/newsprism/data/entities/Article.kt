package com.sarmad.newsprism.data.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarmad.newsprism.utils.Constants.Companion.ARTICLES_TABLE_NAME
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Entity(
    tableName = ARTICLES_TABLE_NAME
)

@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
): Parcelable