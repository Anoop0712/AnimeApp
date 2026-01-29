package com.example.animeapp.di

import android.content.Context
import com.example.animeapp.ApplicationContext
import com.example.animeapp.data.local.AnimeDao
import com.example.animeapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideRecentlyViewedDao(appDatabase: AppDatabase): AnimeDao {
        return appDatabase.animeDao()
    }
}