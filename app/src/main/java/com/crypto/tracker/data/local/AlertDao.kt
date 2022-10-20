package com.crypto.tracker.data.local

import androidx.room.*
import com.crypto.tracker.model.Alert
import kotlinx.coroutines.flow.Flow

/**
 * Data access object (Dao) for [com.crypto.tracker.model.Alert] model.
 */
@Dao
@SuppressWarnings("unused")
interface AlertDao {

    @Query("SELECT * FROM alerts")
    fun getAllAlerts(): Flow<List<Alert>>

    @Query("SELECT * FROM alerts WHERE coinId = :coinId")
    fun getAllAlertsForCoin(coinId: String): Flow<List<Alert>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alert: Alert)

    @Delete
    suspend fun delete(alert: Alert)

    @Query("DELETE FROM alerts")
    suspend fun deleteAll()

    @Query("DELETE FROM alerts WHERE coinId = :coinId")
    suspend fun deleteAllForCoin(coinId: String)

}