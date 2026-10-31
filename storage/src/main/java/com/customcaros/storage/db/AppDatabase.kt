package com.customcaros.storage.db

import androidx.room.*
import com.customcaros.storage.db.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Query("SELECT * FROM music_tracks ORDER BY dateAdded DESC")
    suspend fun getAllTracks(): List<TrackEntity>

    @Query("SELECT * FROM music_tracks WHERE id = :id")
    suspend fun getTrackById(id: String): TrackEntity?

    @Query("SELECT * FROM music_tracks WHERE title LIKE :query OR artist_name LIKE :query OR album_name LIKE :query")
    suspend fun searchTracks(query: String): List<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<TrackEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Delete
    suspend fun deleteTrack(track: TrackEntity)

    @Query("DELETE FROM music_tracks")
    suspend fun clearAllTracks()

    @Query("SELECT COUNT(*) FROM music_tracks")
    suspend fun countTracks(): Int
}

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlists ORDER BY createdAt DESC")
    suspend fun getAllPlaylists(): List<PlaylistEntity>

    @Query("SELECT * FROM playlists WHERE id = :id")
    suspend fun getPlaylistById(id: String): PlaylistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylists(playlists: List<PlaylistEntity>)

    @Delete
    suspend fun deletePlaylist(playlist: PlaylistEntity)

    @Query("DELETE FROM playlists")
    suspend fun clearAllPlaylists()
}

@Dao
interface PlaylistTrackDao {

    @Query("SELECT trackId FROM playlist_tracks WHERE playlistId = :playlistId")
    suspend fun getTrackIdsForPlaylist(playlistId: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistTracks(tracks: List<PlaylistTrackEntity>)

    @Query("DELETE FROM playlist_tracks WHERE playlistId = :playlistId")
    suspend fun clearPlaylistTracks(playlistId: String)

    @Query("DELETE FROM playlist_tracks WHERE trackId = :trackId")
    suspend fun removeTrackFromAllPlaylists(trackId: String)
}

@Dao
interface DestinationDao {

    @Query("SELECT * FROM saved_destinations ORDER BY createdAt DESC")
    suspend fun getAllDestinations(): List<DestinationEntity>

    @Query("SELECT * FROM saved_destinations WHERE id = :id")
    suspend fun getDestinationById(id: String): DestinationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(destination: DestinationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestinations(destinations: List<DestinationEntity>)

    @Delete
    suspend fun deleteDestination(destination: DestinationEntity)

    @Query("DELETE FROM saved_destinations")
    suspend fun clearAllDestinations()
}

@Dao
interface VehicleSettingDao {

    @Query("SELECT value FROM vehicle_settings WHERE key = :key")
    suspend fun getSetting(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSetting(setting: VehicleSettingEntity)

    @Query("DELETE FROM vehicle_settings WHERE key = :key")
    suspend fun deleteSetting(key: String)
}

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackEntity::class,
        DestinationEntity::class,
        VehicleSettingEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun playlistTrackDao(): PlaylistTrackDao
    abstract fun destinationDao(): DestinationDao
    abstract fun vehicleSettingDao(): VehicleSettingDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "custom_car_os.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
