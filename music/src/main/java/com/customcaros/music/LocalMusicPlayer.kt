package com.customcaros.music

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionResult
import androidx.media3.session.SessionToken
import com.customcaros.core.models.MusicTrack
import com.customcaros.storage.StorageFacade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * LocalMusicPlayer manages playback of local audio files using Media3 ExoPlayer.
 * Supports MP3, FLAC, AAC, WAV and other Android-supported formats.
 */
class LocalMusicPlayer private constructor(
    private val context: Context,
    private val scope: CoroutineScope
) {

    private val player: ExoPlayer = ExoPlayer.Builder(context).build()
    /** ExoPlayer Java referansı — MediaSession ve dış erişim için. */
    val exoPlayer: ExoPlayer get() = player
    private val _currentTrack = MutableStateFlow<MusicTrack?>(null)
    val currentTrack: Flow<MusicTrack?> = _currentTrack.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: Flow<Boolean> = _isPlaying.asStateFlow()

    private val _positionMs = MutableStateFlow(0L)
    val positionMs: Flow<Long> = _positionMs.asStateFlow()

    private val _durationMs = MutableStateFlow(0L)
    val durationMs: Flow<Long> = _durationMs.asStateFlow()

    private var currentTrackIndex: Int = -1
    private var playlist: List<MusicTrack> = emptyList()

    init {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                _isPlaying.update { playbackState == Player.STATE_READY && player.isPlaying }
            }
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.update { isPlaying }
            }
            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                _isPlaying.update { false }
            }
        })
    }

    fun loadPlaylist(tracks: List<MusicTrack>, startIndex: Int = 0) {
        playlist = tracks
        currentTrackIndex = startIndex.constrainTo(0, tracks.size - 1)
        if (tracks.isNotEmpty()) {
            loadTrack(tracks[currentTrackIndex])
        }
    }

    private fun loadTrack(track: MusicTrack) {
        val uri = Uri.parse(track.filePath)
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        _currentTrack.update { track }
        _durationMs.update { track.durationMs }
        _positionMs.update { 0L }
    }

    fun play() {
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun next() {
        if (playlist.isEmpty()) return
        currentTrackIndex = (currentTrackIndex + 1).constrainTo(0, playlist.size - 1)
        loadTrack(playlist[currentTrackIndex])
        play()
    }

    fun previous() {
        if (playlist.isEmpty()) return
        currentTrackIndex = (currentTrackIndex - 1).constrainTo(0, playlist.size - 1)
        loadTrack(playlist[currentTrackIndex])
        play()
    }

    fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }

    fun setVolume(volume: Float) {
        player.setVolume(volume)
    }

    fun release() {
        player.release()
    }

    /**
     * Scans the device's shared storage for audio files (music folder only).
     * Privacy: only scans media store audio, no personal files.
     */
    fun scanLocalMusic(context: Context): List<MusicTrack> {
        val tracks = mutableListOf<MusicTrack>()
        val resolver: ContentResolver = context.contentResolver

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} = 1"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        resolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                val artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
                val data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                val duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))

                if (data != null && File(data).exists()) {
                    tracks.add(
                        MusicTrack(
                            id = id,
                            title = title,
                            artist = artist,
                            album = album,
                            filePath = data,
                            durationMs = duration,
                            sizeBytes = size,
                            albumArtPath = null
                        )
                    )
                }
            }
        }

        return tracks
    }

    fun persistPlaylist(tracks: List<MusicTrack>) {
        val facade = StorageFacade.get()
        // Persist to Room as needed
        // (Room insert calls omitted for brevity; can be implemented per-track)
    }

    companion object {
        @Volatile private var instance: LocalMusicPlayer? = null

        fun init(context: Context, scope: CoroutineScope): LocalMusicPlayer {
            return instance ?: synchronized(this) {
                instance = LocalMusicPlayer(context.applicationContext, scope)
                instance!!
            }
        }

        fun get(): LocalMusicPlayer {
            if (instance == null) {
                throw IllegalStateException("LocalMusicPlayer not initialized. Call init(context, scope) first.")
            }
            return instance!!
        }

        fun destroy() {
            instance?.release()
            instance = null
        }
    }

    private fun Int.constrainTo(min: Int, max: Int): Int = when {
        this < min -> min
        this > max -> max
        else -> this
    }
}

/**
 * MediaSessionService for background playback and lock-screen controls.
 * Integrates with MediaSession for Bluetooth media button handling.
 */
class CarMusicSessionService : MediaSessionService() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onGetSession(controllerInfo: androidx.media3.session.MediaSession.ControllerInfo): MediaSession? {
        val player = LocalMusicPlayer.get()
        val session = MediaSession.Builder(this, player.exoPlayer)
            .build()
        return session
    }
}
