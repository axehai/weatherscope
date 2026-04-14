package com.axehai.weatherscope.presentation.weather_screen

import com.axehai.weatherscope.domain.model.LocationSearchResult

sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data class Success(val results: List<LocationSearchResult>) : SearchState
    data class Error(val message: String) : SearchState
}

data class WeatherState(
    val searchState: SearchState = SearchState.Idle
)
