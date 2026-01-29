package com.example.animeapp.di

import androidx.lifecycle.ViewModel
import com.example.animeapp.presentation.AnimeViewmodel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule : ViewModelFactoryModule() {
    @Binds
    @IntoMap
    @ViewModelKey(AnimeViewmodel::class)
    abstract fun appHomeViewModel(animeViewmodel: AnimeViewmodel): ViewModel
}