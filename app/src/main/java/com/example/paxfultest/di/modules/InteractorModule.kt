package com.example.paxfultest.di.modules

import com.example.paxfultest.domain.interactors.api.ApiInteractor
import com.example.paxfultest.domain.interactors.api.IApiInteractor
import com.example.paxfultest.domain.interactors.storage.IStorageInteractor
import com.example.paxfultest.domain.interactors.storage.StorageInteractor
import dagger.Binds
import dagger.Module

@Module
abstract class InteractorModule {

    @Binds
    abstract fun bindApiInteractor(apiInteractor: ApiInteractor) : IApiInteractor

    @Binds
    abstract fun bindStorageInteractor(storageInteractor: StorageInteractor) : IStorageInteractor
}