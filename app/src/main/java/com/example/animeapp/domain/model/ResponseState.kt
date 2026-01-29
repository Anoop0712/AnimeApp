package com.example.animeapp.domain.model

import com.squareup.moshi.JsonClass

sealed class ResponseState<out T> {

    object Loading : ResponseState<Nothing>()

    data class Success<T>(val data: T) : ResponseState<T>()

    data class Error(
        val errorType: ErrorType,
    ) : ResponseState<Nothing>()

    object Idle : ResponseState<Nothing>()
}

const val NO_INTERNET_ERROR = "Sin conexión a Internet"
const val UNKNOWN_ERROR = "Error desconocido"
const val SERVER_ERROR = "Error de servidor"
const val BASE_URL_NOT_SET_TEXT = "Required field base url is missing"
const val INVALID_URL_TEXT = "Invalid Url"
const val INITIAL_STATE = "initial state"

enum class ErrorType(val error: ErrorBody) {

    NO_INTERNET_CONNECTION(ErrorBody(message = NO_INTERNET_ERROR)),
    BASE_URL_NOT_SET(ErrorBody(message = BASE_URL_NOT_SET_TEXT)),
    INVALID_URL(ErrorBody(message = INVALID_URL_TEXT)),
    UNKNOWN(ErrorBody(message = UNKNOWN_ERROR)),
    SERVER(ErrorBody(message = SERVER_ERROR)),
    NO_DATA(ErrorBody(message = SERVER_ERROR)),
    REQUEST_TIME_OUT(ErrorBody(message = SERVER_ERROR)),
    DEFAULT(ErrorBody(message = INITIAL_STATE));
    fun value(): ErrorBody {
        return error
    }
}

@JsonClass(generateAdapter = true)
data class ErrorBody(
    val code: String = "",
    val message: String = "",
    val providerError: ProviderError = ProviderError.EMPTY
) {
    companion object {
        val EMPTY = ErrorBody("", "", ProviderError.EMPTY)
    }
}

@JsonClass(generateAdapter = true)
data class ProviderError(
    val status: String?,
    val code: String?,
    val message: String?
) {
    companion object {
        val EMPTY = ProviderError("", "", "")
    }
}