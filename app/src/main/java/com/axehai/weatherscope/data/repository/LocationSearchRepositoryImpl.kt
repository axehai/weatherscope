package com.axehai.weatherscope.data.repository

import com.axehai.weatherscope.common.Resource
import com.axehai.weatherscope.data.remote.openmeteo.geocoding.OpenMeteoGeocodingService
import com.axehai.weatherscope.data.remote.openmeteo.geocoding.mapper.toLocationSearchResult
import com.axehai.weatherscope.data.remote.utils.safeApiCall
import com.axehai.weatherscope.domain.model.LocationSearchResult
import com.axehai.weatherscope.domain.repository.LocationSearchRepository
import javax.inject.Inject

class LocationSearchRepositoryImpl @Inject constructor(
    private val geocodingService: OpenMeteoGeocodingService
) : LocationSearchRepository {
    override suspend fun search(query: String): Resource<List<LocationSearchResult>> {
        if (query.isBlank()) return Resource.Success(emptyList())
        return safeApiCall {
            geocodingService.searchLocations(query)
                .results
                ?.map { it.toLocationSearchResult() }
                ?: emptyList()
        }
    }
}
