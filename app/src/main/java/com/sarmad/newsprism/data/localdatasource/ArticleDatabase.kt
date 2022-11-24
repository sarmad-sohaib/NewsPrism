package com.sarmad.newsprism.data.localdatasource

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.data.entities.ArticleRemoteKey

@Database(
    entities = [Article::class, ArticleRemoteKey::class],
    version = 14
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeyDao(): RemoteKeysDao
}