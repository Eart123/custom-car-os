package com.customcaros.storage.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.io.Serializable

@Entity(tableName = "music_tracks")
data class TrackEntity(
    @PrimaryKey val id: String,
    val title: String,
    @ColumnInfo(name = "artist_name") val artist: String?,
    @ColumnInfo(name = "album_name") val album: String?,
    val filePath: String,
    val durationMs: Long,
    val sizeBytes: Long,
    @ColumnInfo(name = "album_art_path") val albumArtPath: String?,
    val dateAdded: Long
) : Serializable

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey val id: String,
    val name: String,
    val createdAt: Long
) : Serializable

@Entity(
    tableName = "playlist_tracks",
    primaryKeys = ["playlistId", "trackId"]
)
data class PlaylistTrackEntity(
    val playlistId: String,
    val trackId: String
)

@Entity(tableName = "saved_destinations")
data class DestinationEntity(
    @PrimaryKey val id: String,
    val label: String,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo(name = "address_text") val address: String?,
    val createdAt: Long
) : Serializable

@Entity(tableName = "vehicle_settings")
data class VehicleSettingEntity(
    @PrimaryKey val key: String,
    val value: String
)
