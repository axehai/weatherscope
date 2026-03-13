package com.axehai.weatherscope.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.axehai.weatherscope.data.local.mapper.toDomain
import com.axehai.weatherscope.data.local.mapper.toStored
import com.axehai.weatherscope.data.local.model.StoredActiveLocation
import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.repository.ActiveLocationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

class ActiveLocationRepositoryImpl @Inject constructor(
	private val dataStore: DataStore<StoredActiveLocation>
) : ActiveLocationRepository {
	override suspend fun getActiveLocationOrNull(): ActiveLocation? {
		return try {
			val stored = dataStore.data.first()
			if (stored.sourceId == StoredActiveLocation.SOURCE_UNINITIALIZED) null
			else stored.toDomain()
		} catch (_: IOException) {
			null
		}
	}

	override fun observeActiveLocation(): Flow<ActiveLocation> = dataStore.data
		.retryWhen { cause, attempt ->
			if (cause is IOException && attempt < 3) {
				delay(1000)
				true
			} else {
				false
			}
		}
		.filter { it.sourceId != StoredActiveLocation.SOURCE_UNINITIALIZED }
		.map { it.toDomain() }
		.distinctUntilChanged()


	override suspend fun setActiveLocation(location: ActiveLocation) {
		dataStore.updateData { _ ->
			location.toStored()
		}
	}
}
