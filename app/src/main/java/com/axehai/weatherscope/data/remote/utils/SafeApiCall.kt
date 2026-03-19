package com.axehai.weatherscope.data.remote.utils

import com.axehai.weatherscope.common.Resource
import kotlinx.coroutines.CancellationException

/**
 * Safely executes a suspending [block] of code and wraps its result in a [Resource].
 *
 * If the [block] completes successfully, its result is wrapped in [Resource.Success].
 * If an [Exception] (other than [CancellationException]) occurs, its message is wrapped in [Resource.Error].
 *
 * @param T The type of data returned by the [block].
 * @param block The suspending block of code to execute.
 * @return A [Resource] representing the outcome:
 * - [Resource.Success] containing the data if the block succeeds.
 * - [Resource.Error] containing the error message if an exception occurs.
 * @throws CancellationException if the coroutine is canceled; this exception is rethrown
 * to ensure proper coroutine lifecycle management.
 */
suspend fun <T> safeApiCall(block: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(block())
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Resource.Error(e.localizedMessage ?: "Unknown exception happened")
    }
}
