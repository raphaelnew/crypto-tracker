package com.crypto.tracker.di

import android.content.Context
import androidx.room.Room
import com.crypto.tracker.data.local.AlertDao
import com.crypto.tracker.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * Database related instances.
 */
@Module
@InstallIn(SingletonComponent::class)
@SuppressWarnings("unused")
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    fun provideAlertDao(appDatabase: AppDatabase): AlertDao {
        return appDatabase.alertDao()
    }
}