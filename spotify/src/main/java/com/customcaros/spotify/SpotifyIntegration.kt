package com.customcaros.spotify

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.customcaros.designsystem.components.AutoCard
import com.customcaros.designsystem.theme.AutomotiveTheme

/**
 * SpotifyIntegration — Basit Spotify medya kontrol paneli.
 * Referans görseldeki sağ taraf panelini taklit eder.
 *
 * Yaklaşım: Spotify'ın resmi API'si KULLANILMIYOR.
 * - Spotify uygulaması Intent ile açılır
 * - Oynatma duraklatma, sonraki, önceki için Android media key
 *   olayları Spotify'a gönderilir
 * - API anahtarı gerekmez, ek kütüphane yok
 * - Spotify çevrimdışı indirmeleri bu uygulama tarafından erişilmez
 */
@Composable
fun SpotifyIntegration(
    context: Context,
    modifier: Modifier = Modifier,
    onPlayPause: () -> Unit = {},
    onNext: () -> Unit = {},
    onPrevious: () -> Unit = {}
) {
    var isSpotifyInstalled by remember { mutableStateOf(checkSpotifyInstalled(context)) }
    var isPlaying by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        // Album art area (top right)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    color = Color(0xFF1A1A1A.toInt()),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
                .align(Alignment.Top)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Spotify logo (top right corner)
                Box(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "Spotify",
                        tint = Color(0xFF1DB954.toInt()),
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Album art placeholder
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                        .background(Color(0xFF2A2A2A.toInt()))
                        .align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.Album,
                        contentDescription = "Album art",
                        tint = Color(0xFFB0B0B0.toInt()),
                        modifier = Modifier.size(80.dp).align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Falling",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    ),
                    color = Color.White
                )
                Text(
                    text = "Madeaux",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB0B0B0.toInt())
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Media controls
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Like button
            IconButton(
                onClick = {},
                modifier = Modifier.size(40.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFB0B0B0.toInt())
                ),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like",
                    tint = Color(0xFFB0B0B0.toInt())
                )
            }

            // Previous
            IconButton(
                onClick = { sendMediaKey(context, android.view.KeyEvent.KEYCODE_MEDIA_PREVIOUS); onPrevious() },
                modifier = Modifier.size(48.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFF2A2A2A.toInt()),
                    contentColor = Color.White
                ),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    tint = Color.White
                )
            }

            // Play / Pause
            IconButton(
                onClick = { sendMediaKey(context, android.view.KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE); isPlaying = !isPlaying; onPlayPause() },
                modifier = Modifier.size(56.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFF00D4FF.toInt()),
                    contentColor = Color(0xFF000000.toInt())
                ),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color(0xFF000000.toInt())
                )
            }

            // Next
            IconButton(
                onClick = { sendMediaKey(context, android.view.KeyEvent.KEYCODE_MEDIA_NEXT); onNext() },
                modifier = Modifier.size(48.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFF2A2A2A.toInt()),
                    contentColor = Color.White
                ),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next",
                    tint = Color.White
                )
            }

            // Queue / List
            IconButton(
                onClick = {},
                modifier = Modifier.size(40.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFB0B0B0.toInt())
                ),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Queue",
                    tint = Color(0xFFB0B0B0.toInt())
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Library tabs
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Library",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LibraryChip(label = "Playlists", isSelected = true)
            LibraryChip(label = "Artists", isSelected = false)
            LibraryChip(label = "Albums", isSelected = false)
            LibraryChip(label = "Podcasts", isSelected = false)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Info note
        AutoCard(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            elevation = 0.dp,
            backgroundColor = Color(0xFF141414.toInt())
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Spotify Medya Kontrolü",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF1DB954.toInt())
                )
                Text(
                    text = "• Spotify'ın resmi API'si kullanılmıyor (API anahtarı gerekmez)\n" +
                    "• Medya tuşları Android üzerinden Spotify'a iletilir\n" +
                    "• Spotify çevrimdışı indirmeleri erişilmez\n" +
                    "• Cihaza yük bindirmez",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF8E8E8E.toInt()),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Fill remaining space
        Spacer(modifier = Modifier.weight(1f))
    }
}

/**
 * Library tab chip.
 */
@Composable
private fun LibraryChip(label: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .padding(horizontal = 12.dp)
            .background(
                color = if (isSelected) Color(0xFF2A2A2A.toInt()) else Color.Transparent,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Medium else androidx.compose.ui.text.font.FontWeight.Normal
            ),
            color = if (isSelected) Color.White else Color(0xFFB0B0B0.toInt()),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

/**
 * Spotify uygulamasının cihazda kurulu olup olmadığını kontrol eder.
 */
private fun checkSpotifyInstalled(context: Context): Boolean {
    return try {
        context.packageManager.getPackageInfo("com.spotify.music", 0)
        true
    } catch (e: android.content.pm.PackageManager.NameNotFoundException) {
        false
    }
}

/**
 * Android media key olayı gönderir (Spotify gibi medya uygulamaları tarafından yakalanır).
 */
private fun sendMediaKey(context: Context, keyCode: Int) {
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val downEvent = android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, keyCode)
    val upEvent = android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, keyCode)
    audioManager.dispatchMediaKeyEvent(downEvent)
    audioManager.dispatchMediaKeyEvent(upEvent)
}
