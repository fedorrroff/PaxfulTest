package com.example.paxfultest.di.components

import android.content.Context
import com.example.paxfultest.MainActivity
import com.example.paxfultest.di.modules.MainActivityContextModule
import com.example.paxfultest.di.modules.ViewModelModule
import com.example.paxfultest.di.qualifiers.ActivityContext
import com.example.paxfultest.di.scopes.ActivityScope
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [MainActivityContextModule::class, ViewModelModule::class])
interface ActivityComponent {

    @ActivityContext
    fun getContext() : Context

    fun inject(mainActivity: MainActivity)
}