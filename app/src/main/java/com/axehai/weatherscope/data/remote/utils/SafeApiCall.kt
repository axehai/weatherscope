package com.axehai.weatherscope.data.remote.utils

import com.axehai.weatherscope.common.Resource
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.CancellationException
import java.io.IOException

/**
 * Executes a suspending network [block] and encapsulates the result in a [Resource].
 *
 * This utility ensures a safe execution of API calls by:
 * - Wrapping successful results in [Resource.Success].
 * - Catching [Exception]s and transforming them into human-readable error messages using [toPublicMessage].
 * - Explicitly rethrowing [CancellationException] to maintain proper coroutine cancellation flow.
 *
 * @param T The type of data expected from the network call.
 * @param block The suspending operation to perform.
 * @return A [Resource] instance representing the result of the operation.
 * @throws CancellationException if the coroutine is canceled during execution.
 */
suspend fun <T> safeApiCall(block: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Resource.Error(e.toPublicMessage())
    }
}

/**
 * Maps technical [Exception]s to user-friendly error messages.
 *
 * This extension function sanitizes low-level exceptions (like network timeouts or server errors)
 * into strings suitable for display in the UI, protecting the user from technical jargon
 * while providing enough context about the failure.
 *
 * @return A descriptive error message string.
 */
private fun Exception.toPublicMessage(): String = when (this) {
    is IOException -> "No internet connection"
    is ResponseException -> "Server error (${this.response.status.value})"
    else -> "Unable to load data"
}
