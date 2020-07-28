package com.example.paxfultest.di.modules

import android.content.Context
import com.example.paxfultest.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun provideContext(): Context = context

}