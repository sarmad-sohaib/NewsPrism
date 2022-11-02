package com.sarmad.newsprism.data.localdatasource

import androidx.room.TypeConverter
import com.sarmad.newsprism.data.entities.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source) = source.name

    @TypeConverter
    fun toSource(name: String): Source = Source(name)
}