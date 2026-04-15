package com.axehai.weatherscope.di

import com.axehai.weatherscope.data.repository.ActiveLocationRepositoryImpl
import com.axehai.weatherscope.data.repository.LocationSearchRepositoryImpl
import com.axehai.weatherscope.domain.repository.ActiveLocationRepository
import com.axehai.weatherscope.domain.repository.LocationSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindActiveLocationRepository(impl: ActiveLocationRepositoryImpl): ActiveLocationRepository

    @Binds
    @Singleton
    abstract fun bindLocationSearchRepository(impl: LocationSearchRepositoryImpl): LocationSearchRepository
}
