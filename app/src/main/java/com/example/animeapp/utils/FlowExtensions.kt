package com.example.animeapp.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.startFlow(loader: T): Flow<T> {
    return this.onStart { emit(loader) }
}

fun <T> Flow<T>.onError(converter: Converter<Throwable, T>): Flow<T> {
    return this.catch { emit(converter.apply(it)) }
}

interface Converter<T, R> {

    fun apply(input: T): R
}