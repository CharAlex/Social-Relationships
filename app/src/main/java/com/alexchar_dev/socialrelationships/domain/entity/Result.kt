package com.alexchar_dev.socialrelationships.domain.entity

sealed class Result<out T: Any>{
    data class Value<out T: Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()


}