package com.axehai.weatherscope.domain.use_case.search_locations

import com.axehai.weatherscope.common.Resource
import com.axehai.weatherscope.domain.model.LocationSearchResult
import com.axehai.weatherscope.domain.repository.LocationSearchRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchLocationsUseCaseTest {

    @Test
    fun `returns success with results from repository`() = runTest {
        val results = listOf(
            LocationSearchResult(name = "Delhi", country = "India", admin1 = "Delhi", latitude = 28.6139, longitude = 77.2090),
            LocationSearchResult(name = "New Delhi", country = "India", admin1 = "Delhi", latitude = 28.6448, longitude = 77.2167),
        )
        val fakeRepository = FakeLocationSearchRepository(Resource.Success(results))
        val useCase = SearchLocationsUseCase(fakeRepository)

        val result = useCase("Delhi")

        assertEquals(Resource.Success(results), result)
    }

    @Test
    fun `returns success with empty list when repository returns no results`() = runTest {
        val fakeRepository = FakeLocationSearchRepository(Resource.Success(emptyList()))
        val useCase = SearchLocationsUseCase(fakeRepository)

        val result = useCase("xyznotaplace")

        assertEquals(Resource.Success(emptyList<LocationSearchResult>()), result)
    }

    @Test
    fun `returns error when repository returns error`() = runTest {
        val fakeRepository = FakeLocationSearchRepository(Resource.Error("No internet connection"))
        val useCase = SearchLocationsUseCase(fakeRepository)

        val result = useCase("Delhi")

        assertEquals(Resource.Error("No internet connection"), result)
    }
}

private class FakeLocationSearchRepository(
    private val response: Resource<List<LocationSearchResult>>
) : LocationSearchRepository {
    override suspend fun search(query: String): Resource<List<LocationSearchResult>> = response
}
