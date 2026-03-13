package com.axehai.weatherscope.domain.model

data class ActiveLocation(
	val latitude: Double,
	val longitude: Double,
	val name: String,
	val country: String?,
	val source: LocationSource,
)

/**
 * Represents the source of an active location in the weather application.
 *
 * This enum defines how the user's location was determined, helping the app track
 * the origin of location data and provide appropriate user feedback.
 *
 * @see ActiveLocation
 */
enum class LocationSource(val sourceId: Int) {
	/**
	 * The default location, typically a predefined location used when no other
	 * location source is available (e.g., a default city set by the application).
	 */
	DEFAULT(1),

	/**
	 * The location sourced from the device's GPS or location services.
	 * This represents the user's current physical location.
	 */
	DEVICE(2),

	/**
	 * The location sourced from a user search query.
	 * This represents a location that the user explicitly searched for.
	 */
	SEARCH(3);

	/**
	 * Companion object for [LocationSource] enum, providing utility functions
	 *
	 */
	companion object {
		/**
		 * Returns the [LocationSource] corresponding to the given [sourceId].
		 *
		 * @param sourceId The integer ID of the location source.
		 * @return The matching [LocationSource], or [DEFAULT] if no match is found.
		 */
		fun fromSourceId(sourceId: Int): LocationSource {
			return entries.firstOrNull { it.sourceId == sourceId } ?: DEFAULT
		}
	}
}
