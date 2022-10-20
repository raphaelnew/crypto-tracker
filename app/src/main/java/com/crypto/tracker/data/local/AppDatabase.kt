package com.crypto.tracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crypto.tracker.data.CoinPriceConverters
import com.crypto.tracker.model.Alert

/**
 * AppDatabase defines the database configuration and serves as the app's main access point to the
 * persisted data.
 */
@Database(entities = [Alert::class], version = 1)
@TypeConverters(CoinPriceConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alertDao(): AlertDao
}