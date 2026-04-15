package com.axehai.weatherscope.presentation.weather_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axehai.weatherscope.common.Resource
import com.axehai.weatherscope.domain.model.toActiveLocation
import com.axehai.weatherscope.domain.model.LocationSearchResult
import com.axehai.weatherscope.domain.use_case.ensure_active_location.EnsureActiveLocationInitializedUseCase
import com.axehai.weatherscope.domain.use_case.search_locations.SearchLocationsUseCase
import com.axehai.weatherscope.domain.use_case.set_active_location.SetActiveLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val ensureActiveLocationInitialized: EnsureActiveLocationInitializedUseCase,
    private val searchLocations: SearchLocationsUseCase,
    private val setActiveLocation: SetActiveLocationUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    private val _events = Channel<WeatherEvent>()
    val events = _events.receiveAsFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch { ensureActiveLocationInitialized() }
        observeSearchQuery()
    }

    fun onAction(action: WeatherAction) {
        when (action) {
            is WeatherAction.OnSearchQueryChanged -> _searchQuery.value = action.query.trim()
            is WeatherAction.OnSearchResultSelected -> selectLocation(action.locationSearchResult)
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                flow {
                    emit(SearchState.Loading)
                    emit(
                        when (val resource = searchLocations(query)) {
                            Resource.Loading -> SearchState.Loading
                            is Resource.Success -> SearchState.Success(resource.data)
                            is Resource.Error -> SearchState.Error(resource.message)
                        }
                    )
                }
            }
            .onEach { searchState ->
                _state.update { it.copy(searchState = searchState) }
            }
            .launchIn(viewModelScope)
    }

    private fun selectLocation(result: LocationSearchResult) {
        viewModelScope.launch {
            setActiveLocation(result.toActiveLocation())
        }
    }
}
