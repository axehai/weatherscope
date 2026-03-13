package com.axehai.weatherscope.data.repository

import androidx.datastore.core.DataStore
import com.axehai.weatherscope.data.local.mapper.toDomain
import com.axehai.weatherscope.data.local.mapper.toStored
import com.axehai.weatherscope.data.local.model.StoredActiveLocation
import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.repository.ActiveLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActiveLocationRepositoryImpl @Inject constructor(
	private val dataStore: DataStore<StoredActiveLocation>
) : ActiveLocationRepository {
	override suspend fun getActiveLocationOrNull(): ActiveLocation? {
		val stored = dataStore.data.first()
		return if (stored.sourceId == StoredActiveLocation.SOURCE_UNINITIALIZED) null
		else stored.toDomain()
	}

	override fun observeActiveLocation(): Flow<ActiveLocation> =
		dataStore.data.filter { it.sourceId != StoredActiveLocation.SOURCE_UNINITIALIZED }
			.map { it.toDomain() }.distinctUntilChanged()


	override suspend fun setActiveLocation(location: ActiveLocation) {
		dataStore.updateData { _ ->
			location.toStored()
		}
	}
}
