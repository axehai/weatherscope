package com.axehai.weatherscope.common


sealed interface Resource<out T> {
    data object Loading : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val error: AppError) : Resource<Nothing>
}

sealed interface AppError {
    data object NetworkError : AppError
    data class Unknown(val throwable: Throwable? = null) : AppError
}
