package com.sarmad.newsprism.data.localdatasource

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sarmad.newsprism.data.entities.Article

@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}