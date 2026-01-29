package com.example.animeapp.di

import com.example.animeapp.presentation.ui.AnimeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScoped


@Module(includes = [ViewModelModule::class])
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun bindAnimeActivity(): AnimeActivity

}