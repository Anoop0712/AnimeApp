package com.example.animeapp

import com.example.animeapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Qualifier

class AnimeApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationContext