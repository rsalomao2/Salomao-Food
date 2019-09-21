package com.salomao.data.pojo

sealed class Status<out T> {
    class Success<T>(val response: T) : Status<T>()
    class Error(val responseError: String) : Status<Nothing>()
}
