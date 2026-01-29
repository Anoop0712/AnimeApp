package com.example.animeapp.di

import android.content.Context
import com.example.animeapp.AnimeApplication
import com.example.animeapp.ApplicationContext
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
        ActivityBindingModule::class,
        AnimeServiceModule::class,
        DatabaseModule::class,
        DispatchersModule::class
    ]
)
interface AppComponent : AndroidInjector<AnimeApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: AnimeApplication): Builder

        fun build(): AppComponent
    }
}

@Module
abstract class AnimeAppContextModule {

    @Singleton
    @Binds
    @ApplicationContext
    abstract fun bindContext(appController: AnimeApplication): Context
}
