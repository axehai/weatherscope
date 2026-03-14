package com.axehai.weatherscope.domain.use_case.ensure_active_location

import com.axehai.weatherscope.domain.defaults.ActiveLocationDefaults
import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.model.LocationSource
import com.axehai.weatherscope.domain.repository.ActiveLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class EnsureActiveLocationInitializedUseCaseTest {

	@Test
	fun `when active location is null, it sets New Delhi`() = runTest {
		val fakeRepository = FakeActiveLocationRepository(initialLocation = null)
		val useCase = EnsureActiveLocationInitializedUseCase(fakeRepository)

		useCase()

		Assert.assertEquals(ActiveLocationDefaults.NEW_DELHI, fakeRepository.savedLocation)
		Assert.assertEquals(1, fakeRepository.setActiveLocationCallCount)
	}

	@Test
	fun `when active location already exists, it does not overwrite it`() = runTest {
		val existingLocation = ActiveLocation(
			latitude = 19.0760,
			longitude = 72.8777,
			name = "Mumbai",
			country = "India",
			source = LocationSource.DEVICE // or LocationSource.SEARCH
		)

		val fakeRepository = FakeActiveLocationRepository(initialLocation = existingLocation)
		val useCase = EnsureActiveLocationInitializedUseCase(fakeRepository)

		useCase()

		Assert.assertNull(fakeRepository.savedLocation)
		Assert.assertEquals(0, fakeRepository.setActiveLocationCallCount)
		Assert.assertTrue(fakeRepository.getActiveLocationOrNullCallCount > 0)
	}
}


private class FakeActiveLocationRepository(
	private val initialLocation: ActiveLocation?
) : ActiveLocationRepository {

	var savedLocation: ActiveLocation? = null
		private set

	var setActiveLocationCallCount: Int = 0
		private set

	var getActiveLocationOrNullCallCount: Int = 0
		private set

	override fun observeActiveLocation(): Flow<ActiveLocation> = emptyFlow()

	override suspend fun setActiveLocation(location: ActiveLocation) {
		savedLocation = location
		setActiveLocationCallCount++
	}

	override suspend fun getActiveLocationOrNull(): ActiveLocation? {
		getActiveLocationOrNullCallCount++
		return initialLocation
	}
}