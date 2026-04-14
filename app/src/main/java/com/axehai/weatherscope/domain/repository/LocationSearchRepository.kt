package com.axehai.weatherscope.domain.repository

import com.axehai.weatherscope.common.Resource
import com.axehai.weatherscope.domain.model.LocationSearchResult

/**
 * Repository for searching geographic locations based on a text query.
 */
interface LocationSearchRepository {
    /**
     * Performs a search for locations matching the [query].
     *
     * @param query The search term entered by the user (e.g., "London").
     * @return A [Resource] wrapping the list of matching [LocationSearchResult]s.
     */
    suspend fun search(query: String): Resource<List<LocationSearchResult>>
}