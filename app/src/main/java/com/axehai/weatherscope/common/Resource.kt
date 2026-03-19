package com.axehai.weatherscope.common

/**
 * A generic wrapper class that represents the state of a resource or data operation.
 *
 * This sealed interface is used to encapsulate different states of data fetching or processing:
 * - [Loading]: The operation is currently in progress.
 * - [Success]: The operation completed successfully with the resulting data.
 * - [Error]: The operation failed with a specific message.
 *
 * @param T The type of data being held by the resource.
 */
sealed interface Resource<out T> {
    /**
     * Represents the loading state of a resource.
     */
    data object Loading : Resource<Nothing>

    /**
     * Represents the success state of a resource, containing the retrieved [data].
     *
     * @param T The type of the data.
     * @property data The result of the successful operation.
     */
    data class Success<T>(val data: T?) : Resource<T>

    /**
     * Represents the error state of a resource, containing a descriptive [message].
     *
     * @property message A string describing the error that occurred.
     */
    data class Error(val message: String) : Resource<Nothing>
}
