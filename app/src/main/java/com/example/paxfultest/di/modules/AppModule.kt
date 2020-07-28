package com.example.paxfultest.di.modules

import com.example.paxfultest.domain.storage.keyvalue.IKeyValueStore
import com.example.paxfultest.domain.storage.keyvalue.KeyValueStore
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindKeyValueStore(keyValueStore: KeyValueStore): IKeyValueStore

}