/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yapp.growth.domain

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class NetworkResult<out R> {

    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [NetworkResult] is of type [Success] & holds non-null [Success.data].
 */
val NetworkResult<*>.succeeded
    get() = this is NetworkResult.Success

fun <T> NetworkResult<T>.successOr(fallback: T): T {
    return (this as? NetworkResult.Success<T>)?.data ?: fallback
}

inline fun <T, R> NetworkResult<T>.map(transform: (T) -> R): NetworkResult<R> =
    when (this) {
        is NetworkResult.Success -> NetworkResult.Success(transform(data))
        is NetworkResult.Error -> NetworkResult.Error(this.exception)
        is NetworkResult.Loading -> NetworkResult.Loading
    }

inline fun <T, R> NetworkResult<T>.mapCatching(transform: (T) -> R): NetworkResult<R> =
    when (this) {
        is NetworkResult.Success -> runCatching { transform(data) }
        is NetworkResult.Error -> NetworkResult.Error(this.exception)
        is NetworkResult.Loading -> NetworkResult.Loading
    }

inline fun <T, R> NetworkResult<T>.zip(other: R): NetworkResult<Pair<T, R>> =
    when (this) {
        is NetworkResult.Success -> NetworkResult.Success(data to other)
        is NetworkResult.Error -> NetworkResult.Error(this.exception)
        is NetworkResult.Loading -> NetworkResult.Loading
    }

inline fun <T, R> T.runCatching(transform: (T) -> R): NetworkResult<R> {
    return try {
        NetworkResult.Success(transform(this))
    } catch (e: Throwable) {
        NetworkResult.Error(e as Exception)
    }
}

inline fun <T> NetworkResult<T>.runCatchingWith(consumer: (T) -> Unit): NetworkResult<T> =
    when (this) {
        is NetworkResult.Success -> try {
            consumer(data)
            this
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
        is NetworkResult.Error -> NetworkResult.Error(this.exception)
        is NetworkResult.Loading -> NetworkResult.Loading
    }

suspend fun <T, R> NetworkResult<T>.flatMap(transform: suspend (T) -> NetworkResult<R>) =
    when (this) {
        is NetworkResult.Success -> transform(data)
        is NetworkResult.Error -> NetworkResult.Error(this.exception)
        is NetworkResult.Loading -> NetworkResult.Loading
    }

suspend fun <T, R, U> Pair<NetworkResult<T>, NetworkResult<R>>.map(transform: suspend (T, R) -> U): NetworkResult<U> =
    when {
        (first.succeeded) && (second.succeeded) -> NetworkResult.Success(
            transform(
                (first as NetworkResult.Success).data,
                (second as NetworkResult.Success).data
            )
        )
        (first.succeeded) && (second.succeeded).not() -> NetworkResult.Error((second as NetworkResult.Error).exception)
        (first.succeeded).not() && (second.succeeded) -> NetworkResult.Error((first as NetworkResult.Error).exception)
        (first.succeeded).not() && (second.succeeded).not() -> NetworkResult.Error((first as NetworkResult.Error).exception)
        else -> NetworkResult.Loading
    }

suspend fun <T> NetworkResult<T>.merge(supplier: suspend () -> NetworkResult<T>): NetworkResult<List<T>> =
    when (this) {
        is NetworkResult.Error -> NetworkResult.Error(this.exception)
        is NetworkResult.Loading -> NetworkResult.Loading
        is NetworkResult.Success -> {
            when (val result = supplier.invoke()) {
                is NetworkResult.Error -> NetworkResult.Error(result.exception)
                is NetworkResult.Loading -> NetworkResult.Loading
                is NetworkResult.Success -> NetworkResult.Success(listOf(this.data, result.data))
            }
        }
    }

suspend fun <T> NetworkResult<List<T>>.add(supplier: suspend () -> NetworkResult<T>): NetworkResult<List<T>> =
    when (this) {
        is NetworkResult.Error -> NetworkResult.Error(this.exception)
        is NetworkResult.Loading -> NetworkResult.Loading
        is NetworkResult.Success -> {
            when (val result = supplier.invoke()) {
                is NetworkResult.Error -> NetworkResult.Error(result.exception)
                is NetworkResult.Loading -> NetworkResult.Loading
                is NetworkResult.Success -> NetworkResult.Success(data + result.data)
            }
        }
    }

inline fun <T> NetworkResult<T>.onSuccess(action: (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) action(data)
    return this
}

inline fun <T> NetworkResult<T>.onError(action: (Exception) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Error) action(exception)
    return this
}

inline fun <T> NetworkResult<T>.mapError(transformWithError: (Exception) -> Exception): NetworkResult<T> {
    return when (this) {
        is NetworkResult.Success -> this
        is NetworkResult.Error -> NetworkResult.Error(transformWithError(exception))
        is NetworkResult.Loading -> this
    }
}

inline fun <R, T : R> NetworkResult<T>.getOrElse(onError: (exception: Throwable) -> R): R {
    return when (this) {
        is NetworkResult.Success -> data
        is NetworkResult.Error -> onError(exception)
        is NetworkResult.Loading -> error("not support loading type")
    }
}

suspend fun <T, U> Pair<NetworkResult<T>, NetworkResult<U>>.combine(onSuccess: suspend (Pair<T, U>) -> Unit, onError: suspend (exception: Exception) -> Unit) {
    first.onSuccess { t ->
        second.onSuccess { u ->
            onSuccess(Pair(t, u))
        }.onError { onError(it) }
    }.onError { onError(it) }
}

suspend fun <T, U, V> Triple<NetworkResult<T>, NetworkResult<U>, NetworkResult<V>>.combine(onSuccess: suspend (Triple<T, U, V>) -> Unit, onError: suspend (exception: Exception) -> Unit) {
    first.onSuccess { t ->
        second.onSuccess { u ->
            third.onSuccess { v ->
                onSuccess(Triple(t, u, v))
            }.onError { onError(it) }
        }.onError { onError(it) }
    }.onError { onError(it) }
}
