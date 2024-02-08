package ru.testapp.hotel_test.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.testapp.hotel_test.repository.Repository
import ru.testapp.hotel_test.repository.RepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsHotelRepository(
        impl: RepositoryImpl
    ): Repository
}