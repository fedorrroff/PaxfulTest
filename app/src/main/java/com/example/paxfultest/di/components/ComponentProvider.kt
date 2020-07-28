package com.example.paxfultest.di.components

import android.content.Context
import com.example.paxfultest.di.modules.ContextModule
import com.example.paxfultest.di.modules.RetrofitModule

object ComponentProvider {

    fun provideApplicationComponent(context: Context): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .contextModule(ContextModule(context))
            .retrofitModule(RetrofitModule())
            .build()

}