package com.axehai.weatherscope.domain.use_case.search_locations

import com.axehai.weatherscope.common.Resource
import com.axehai.weatherscope.domain.model.LocationSearchResult
import com.axehai.weatherscope.domain.repository.LocationSearchRepository
import javax.inject.Inject

class SearchLocationsUseCase @Inject constructor(
    private val repository: LocationSearchRepository
) {
    suspend operator fun invoke(query: String): Resource<List<LocationSearchResult>> =
        repository.search(query)
}
