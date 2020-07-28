package com.example.paxfultest.di.components

import android.content.Context
import com.example.paxfultest.di.modules.*
import com.example.paxfultest.di.scopes.ApplicationScope
import com.example.paxfultest.domain.api.APIInterface
import com.example.paxfultest.domain.storage.PaxfulDB
import com.example.paxfultest.screens.jokes.JokesFragment
import com.example.paxfultest.screens.myjokes.MyJokesFragment
import com.example.paxfultest.screens.myjokes.NewJokeDialogFragment
import com.example.paxfultest.screens.settings.SettingsFragment
import dagger.Component

@ApplicationScope
@Component(modules = [
    ContextModule::class,
    RetrofitModule::class,
    DbModule::class,
    InteractorModule::class,
    ViewModelModule::class,
    AppModule::class
])
interface ApplicationComponent {

    fun getApiInterface() : APIInterface

    fun getDB(): PaxfulDB

    fun context() : Context

    fun inject(fragment: JokesFragment)

    fun inject(fragment: SettingsFragment)

    fun inject(fragment: MyJokesFragment)

    fun inject(fragmentDialog: NewJokeDialogFragment)
}