package com.example.paxfultest.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paxfultest.MainViewModel
import com.example.paxfultest.screens.jokes.JokesViewModel
import com.example.paxfultest.screens.myjokes.MyJokesViewModel
import com.example.paxfultest.screens.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(JokesViewModel::class)
    abstract fun bindJokeViewModel(viewModel: JokesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyJokesViewModel::class)
    abstract fun bindMyJokesViewModel(viewModel: MyJokesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}