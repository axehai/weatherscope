package com.axehai.weatherscope.presentation.weather_screen

import app.cash.turbine.turbineScope
import com.axehai.weatherscope.common.Resource
import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.model.LocationSearchResult
import com.axehai.weatherscope.domain.model.LocationSource
import com.axehai.weatherscope.domain.repository.ActiveLocationRepository
import com.axehai.weatherscope.domain.repository.LocationSearchRepository
import com.axehai.weatherscope.domain.use_case.ensure_active_location.EnsureActiveLocationInitializedUseCase
import com.axehai.weatherscope.domain.use_case.search_locations.SearchLocationsUseCase
import com.axehai.weatherscope.domain.use_case.set_active_location.SetActiveLocationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var fakeActiveLocationRepository: FakeActiveLocationRepository
    private lateinit var fakeLocationSearchRepository: FakeLocationSearchRepository
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeActiveLocationRepository = FakeActiveLocationRepository()
        fakeLocationSearchRepository = FakeLocationSearchRepository()
        viewModel = WeatherViewModel(
            ensureActiveLocationInitialized = EnsureActiveLocationInitializedUseCase(fakeActiveLocationRepository),
            searchLocations = SearchLocationsUseCase(fakeLocationSearchRepository),
            setActiveLocation = SetActiveLocationUseCase(fakeActiveLocationRepository),
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --- Initial state ---

    @Test
    fun `initial search state is Idle`() = runTest(testDispatcher) {
        turbineScope {
            val turbine = viewModel.state.testIn(backgroundScope)
            assertEquals(SearchState.Idle, turbine.awaitItem().searchState)
            turbine.cancel()
        }
    }

    // --- Loading → Success / Error transitions ---

    @Test
    fun `search query transitions through Loading then Success`() = runTest(testDispatcher) {
        fakeLocationSearchRepository.response = Resource.Success(listOf(delhi))
        fakeLocationSearchRepository.searchDelayMs = 500 // Suspends between Loading and Success so collector captures Loading

        turbineScope {
            val turbine = viewModel.state.testIn(backgroundScope)
            turbine.awaitItem() // Idle

            viewModel.onAction(WeatherAction.OnSearchQueryChanged("Delhi"))
            this@runTest.advanceTimeBy(301) // Debounce fires; search starts and suspends at delay — Loading is stable

            assertEquals(SearchState.Loading, turbine.awaitItem().searchState)

            this@runTest.advanceTimeBy(501) // Search completes
            assertEquals(SearchState.Success(listOf(delhi)), turbine.awaitItem().searchState)
        }
    }

    @Test
    fun `search query transitions through Loading then Error`() = runTest(testDispatcher) {
        fakeLocationSearchRepository.response = Resource.Error("No internet connection")
        fakeLocationSearchRepository.searchDelayMs = 500

        turbineScope {
            val turbine = viewModel.state.testIn(backgroundScope)
            turbine.awaitItem() // Idle

            viewModel.onAction(WeatherAction.OnSearchQueryChanged("Delhi"))
            this@runTest.advanceTimeBy(301)

            assertEquals(SearchState.Loading, turbine.awaitItem().searchState)

            this@runTest.advanceTimeBy(501)
            assertEquals(SearchState.Error("No internet connection"), turbine.awaitItem().searchState)
        }
    }

    // --- Debounce ---

    @Test
    fun `rapid queries within debounce window trigger only one search`() = runTest(testDispatcher) {
        viewModel.onAction(WeatherAction.OnSearchQueryChanged("D"))
        viewModel.onAction(WeatherAction.OnSearchQueryChanged("De"))
        viewModel.onAction(WeatherAction.OnSearchQueryChanged("Del"))
        advanceTimeBy(301)

        assertEquals(listOf("Del"), fakeLocationSearchRepository.searchedQueries)
    }

    @Test
    fun `query sent before debounce window closes does not trigger a search`() = runTest(testDispatcher) {
        viewModel.onAction(WeatherAction.OnSearchQueryChanged("Delhi"))
        advanceTimeBy(200)

        assertTrue(fakeLocationSearchRepository.searchedQueries.isEmpty())
    }

    // --- distinctUntilChanged ---

    @Test
    fun `same query dispatched twice only triggers one search`() = runTest(testDispatcher) {
        viewModel.onAction(WeatherAction.OnSearchQueryChanged("Delhi"))
        advanceTimeBy(301)
        viewModel.onAction(WeatherAction.OnSearchQueryChanged("Delhi"))
        advanceTimeBy(301)

        assertEquals(1, fakeLocationSearchRepository.searchedQueries.size)
    }

    // --- flatMapLatest (latest-query-wins) ---

    @Test
    fun `result from superseded in-flight query is discarded`() = runTest(testDispatcher) {
        fakeLocationSearchRepository.searchDelayMs = 500
        fakeLocationSearchRepository.response = Resource.Success(listOf(delhi))

        turbineScope {
            val turbine = viewModel.state.testIn(backgroundScope)
            turbine.awaitItem() // Idle

            // First query starts, takes 500ms to complete
            viewModel.onAction(WeatherAction.OnSearchQueryChanged("D"))
            this@runTest.advanceTimeBy(301)
            assertEquals(SearchState.Loading, turbine.awaitItem().searchState)

            // Second query arrives before first completes — cancels first
            viewModel.onAction(WeatherAction.OnSearchQueryChanged("De"))
            this@runTest.advanceTimeBy(301) // "De" passes debounce; state already Loading, no new emission
            this@runTest.advanceTimeBy(501) // "De" completes

            assertTrue(turbine.awaitItem().searchState is SearchState.Success)
            // "D" was started but its result was never applied to state
            assertEquals(listOf("D", "De"), fakeLocationSearchRepository.searchedQueries)
        }
    }

    // --- Action dispatch ---

    @Test
    fun `OnSearchResultSelected sets active location with SEARCH source`() = runTest(testDispatcher) {
        viewModel.onAction(WeatherAction.OnSearchResultSelected(delhi))

        val saved = fakeActiveLocationRepository.activeLocation
        assertEquals(delhi.name, saved?.name)
        assertEquals(delhi.latitude, saved?.latitude)
        assertEquals(delhi.longitude, saved?.longitude)
        assertEquals(LocationSource.SEARCH, saved?.source)
    }
}

// --- Fixture ---

private val delhi = LocationSearchResult(
    name = "Delhi",
    country = "India",
    admin1 = "Delhi",
    latitude = 28.6139,
    longitude = 77.2090,
)

// --- Fakes ---

private class FakeLocationSearchRepository : LocationSearchRepository {
    var response: Resource<List<LocationSearchResult>> = Resource.Success(emptyList())
    var searchDelayMs: Long = 0L
    val searchedQueries = mutableListOf<String>()

    override suspend fun search(query: String): Resource<List<LocationSearchResult>> {
        if (query.isBlank()) return Resource.Success(emptyList())
        searchedQueries.add(query)
        if (searchDelayMs > 0) delay(searchDelayMs)
        return response
    }
}

private class FakeActiveLocationRepository : ActiveLocationRepository {
    var activeLocation: ActiveLocation? = null
        private set

    override fun observeActiveLocation(): Flow<ActiveLocation> = emptyFlow()

    override suspend fun setActiveLocation(location: ActiveLocation) {
        activeLocation = location
    }

    override suspend fun getActiveLocationOrNull(): ActiveLocation? = activeLocation
}
