package com.axehai.weatherscope.data.local.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.axehai.weatherscope.data.local.model.StoredActiveLocation
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object StoredActiveLocationSerializer : Serializer<StoredActiveLocation> {
	override val defaultValue: StoredActiveLocation = StoredActiveLocation()
	val json = Json { ignoreUnknownKeys = true }
	override suspend fun readFrom(input: InputStream): StoredActiveLocation = try {
		json.decodeFromString<StoredActiveLocation>(
			input.readBytes().decodeToString()
		)
	} catch (e: SerializationException) {
		throw CorruptionException("Unable to read StoredActiveLocation", e)
	}

	override suspend fun writeTo(
		t: StoredActiveLocation, output: OutputStream
	) {
		output.write(
			json.encodeToString(t).encodeToByteArray()
		)
	}
}
