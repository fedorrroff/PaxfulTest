package com.example.paxfultest.di.modules

import android.content.Context
import com.example.paxfultest.domain.storage.PaxfulDB
import dagger.Module
import dagger.Provides

@Module
object DbModule {

    @JvmStatic
    @Provides
    fun provideDB(context: Context): PaxfulDB {
        return PaxfulDB.create(context)
    }
}