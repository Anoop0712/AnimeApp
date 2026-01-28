package com.example.animeapp.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MainBaseAppViewModelFactory): ViewModelProvider.Factory
}
