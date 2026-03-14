package com.axehai.weatherscope.domain.use_case.set_active_location

import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.model.LocationSource
import com.axehai.weatherscope.domain.repository.ActiveLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SetActiveLocationUseCaseTest {

	lateinit var fakeRepository: FakeRepository
	lateinit var useCase: SetActiveLocationUseCase

	@Before
	fun setup() {
		fakeRepository = FakeRepository()
		useCase = SetActiveLocationUseCase(fakeRepository)
	}

	@Test
	fun `sets active location in repository`() = runTest {
		val existingLocation = ActiveLocation(
			latitude = 19.0760,
			longitude = 72.8777,
			name = "Mumbai",
			country = "India",
			source = LocationSource.DEVICE // or LocationSource.SEARCH
		)

		useCase.invoke(existingLocation)

		assertEquals(existingLocation, fakeRepository.getActiveLocationOrNull())

	}


}

class FakeRepository : ActiveLocationRepository {

	var activeLocation: ActiveLocation? = null
		private set


	override fun observeActiveLocation(): Flow<ActiveLocation> {
		return emptyFlow()
	}

	override suspend fun setActiveLocation(location: ActiveLocation) {
		activeLocation = location
	}

	override suspend fun getActiveLocationOrNull(): ActiveLocation? {
		return activeLocation
	}

}