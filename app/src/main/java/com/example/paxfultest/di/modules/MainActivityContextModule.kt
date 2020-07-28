package com.example.paxfultest.di.modules

import android.content.Context
import com.example.paxfultest.MainActivity
import com.example.paxfultest.di.qualifiers.ActivityContext
import com.example.paxfultest.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
class MainActivityContextModule (
    private val mainActivity: MainActivity,
    private val context: Context = mainActivity
){

    @Provides
    @ActivityScope
    fun providesMainActivity(): MainActivity {
        return mainActivity
    }

    @Provides
    @ActivityScope
    @ActivityContext
    fun provideContext(): Context {
        return context
    }
}