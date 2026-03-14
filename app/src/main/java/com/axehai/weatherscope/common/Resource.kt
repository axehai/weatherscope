package com.axehai.weatherscope.common

/**
 * Sealed class representing the state of a resource operation.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
	/**
	 * Success state containing the data.
	 */
	class Success<T>(data: T?) : Resource<T>(data)
	/**
	 * Error state with an optional message and data.
	 */
	class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
	/**
	 * Loading state with optional data.
	 */
	class Loading<T>(data: T? = null) : Resource<T>(data)
}
