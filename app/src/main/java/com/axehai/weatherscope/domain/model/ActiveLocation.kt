package com.axehai.weatherscope.domain.model

data class ActiveLocation(
	val latitude: Double,
	val longitude: Double,
	val name:String,
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
enum class LocationSource {
	/**
	 * The default location, typically a predefined location used when no other
	 * location source is available (e.g., a default city set by the application).
	 */
	DEFAULT,

	/**
	 * The location sourced from the device's GPS or location services.
	 * This represents the user's current physical location.
	 */
	DEVICE,

	/**
	 * The location sourced from a user search query.
	 * This represents a location that the user explicitly searched for.
	 */
	SEARCH
}