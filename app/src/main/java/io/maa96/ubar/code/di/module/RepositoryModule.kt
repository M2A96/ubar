package io.maa96.ubar.code.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.maa96.ubar.data.repository.AddressRepositoryImpl
import io.maa96.ubar.domain.repository.AddressRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        addressRepositoryImpl: AddressRepositoryImpl
    ): AddressRepository

}