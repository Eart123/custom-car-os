package com.customcaros.storage

import android.content.Context
import androidx.room.Room
import com.customcaros.storage.db.AppDatabase
import com.customcaros.storage.db.DestinationDao
import com.customcaros.storage.db.PlaylistDao
import com.customcaros.storage.db.PlaylistTrackDao
import com.customcaros.storage.db.TrackDao
import com.customcaros.storage.db.VehicleSettingDao

/**
 * StorageFacade provides a single access point to all local persistence.
 * Wraps Room + DataStore for clean dependency injection.
 */
class StorageFacade private constructor(
    private val database: AppDatabase
) {

    val trackDao: TrackDao = database.trackDao()
    val playlistDao: PlaylistDao = database.playlistDao()
    val playlistTrackDao: PlaylistTrackDao = database.playlistTrackDao()
    val destinationDao: DestinationDao = database.destinationDao()
    val vehicleSettingDao: VehicleSettingDao = database.vehicleSettingDao()

    companion object {
        @Volatile private var instance: StorageFacade? = null

        fun init(context: Context): StorageFacade {
            return instance ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "custom_car_os.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = StorageFacade(db)
                instance!!
            }
        }

        fun get(): StorageFacade {
            if (instance == null) {
                throw IllegalStateException("StorageFacade not initialized. Call init(context) first.")
            }
            return instance!!
        }

        fun destroy() {
            instance?.database?.close()
            instance = null
        }
    }
}
