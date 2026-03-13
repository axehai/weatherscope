package com.axehai.weatherscope.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.axehai.weatherscope.data.local.mapper.toStored
import com.axehai.weatherscope.data.local.model.StoredActiveLocation
import com.axehai.weatherscope.data.local.serializer.StoredActiveLocationSerializer
import com.axehai.weatherscope.domain.defaults.ActiveLocationDefaults.NEW_DELHI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

	@Provides
	@Singleton
	fun provideActiveLocationDataStore(
		@ApplicationContext context: Context
	): DataStore<StoredActiveLocation> {
		return DataStoreFactory.create(
			serializer = StoredActiveLocationSerializer,
			produceFile = { context.dataStoreFile("active_location.json") },
			corruptionHandler = ReplaceFileCorruptionHandler {
				NEW_DELHI.toStored()
			})
	}
}