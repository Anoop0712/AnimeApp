package com.example.animeapp.di

import android.app.Application
import android.content.Context
import com.example.animeapp.AnimeApplication
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AnimeAppContextModule::class,
        AndroidInjectionModule::class,
        ViewModelFactoryModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<AnimeApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}

@Module
abstract class AnimeAppContextModule {

    @Singleton
    @Binds
    abstract fun bindApplication(appController: AnimeApplication): Context
}
