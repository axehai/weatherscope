package com.axehai.weatherscope.presentation.weather_screen

import com.axehai.weatherscope.domain.model.LocationSearchResult

sealed interface WeatherAction {
    data class OnSearchQueryChanged(val query: String) : WeatherAction
    data class OnSearchResultSelected(val locationSearchResult: LocationSearchResult) :
        WeatherAction
}
