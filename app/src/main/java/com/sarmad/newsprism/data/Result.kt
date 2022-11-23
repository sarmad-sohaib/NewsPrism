package com.sarmad.newsprism.data

sealed class Result<out R> {
    data class Success<out T>(val data: T?): Result<T>()
    data class Error(val exception: String): Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null