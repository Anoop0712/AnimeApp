package com.example.animeapp.utils

import com.example.animeapp.domain.model.ErrorType
import com.example.animeapp.domain.model.ResponseState
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandler<T> : Converter<Throwable, ResponseState<T>> {
    override fun apply(input: Throwable): ResponseState.Error {
        return ResponseState.Error(errorViewState(throwable = input))
    }

    private fun errorViewState(throwable: Throwable): ErrorType {
        return when (throwable) {
            is JsonEncodingException -> ErrorType.UNKNOWN
            is UnknownHostException -> ErrorType.NO_INTERNET_CONNECTION
            is SocketTimeoutException -> ErrorType.REQUEST_TIME_OUT
            is NoSuchElementException -> ErrorType.NO_DATA
            is HttpException -> ErrorType.SERVER

            else -> ErrorType.UNKNOWN
        }
    }
}