package com.customcaros.offline.maps

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.customcaros.designsystem.components.AutoCard
import com.customcaros.designsystem.theme.AutomotiveTheme
import com.customcaros.core.network.ConnectionStateHolder
import com.customcaros.core.network.ConnectivityManagerObserver
import com.customcaros.core.network.NetworkState
import kotlinx.coroutines.CoroutineScope

/**
 * OfflineMapsManager — çevrimdışı harita yönetimi.
 *
 * Kullanılan kütüphaneler:
 * - Mapsforge: Harita renderleme (offline .map dosyaları)
 * - GraphHopper: Çevrimdışı rota hesaplama
 *
 * Şu an UI katmanı hazır. Mapsforge native .so ve GraphHopper jar'ı
 * offline-maps/build.gradle.kts içine eklenirse tam aktif olur.
 */
@Composable
fun OfflineMapsManager(
    context: Context,
    scope: CoroutineScope,
    connectionHolder: ConnectionStateHolder,
    modifier: Modifier = Modifier
) {
    val connectivityObserver = remember { ConnectivityManagerObserver.init(context) }
    val isConnected by connectivityObserver.observe()
        .collectAsStateWithLifecycle(initialValue = NetworkState.Unknown)

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Çevrimdışı Haritalar",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Bağlantı durumu kartı
        AutoCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Bağlantı Durumu", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (isConnected) {
                            NetworkState.Connected -> "Online"
                            NetworkState.Disconnected -> "Çevrimdışı"
                            NetworkState.Unknown -> "Bilinmiyor"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isConnected == NetworkState.Connected)
                            Color(0xFF00E676.toInt()) else Color(0xFFFF5252.toInt())
                    )
                    Text(
                        text = if (isConnected == NetworkState.Connected) "Harita güncelleme açık" else "Yerel harita kullanılıyor",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // İndirme bölümü
        AutoCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Harita Bölgesi İndir", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                Text(
                    text = "Offline harita dosyaları (.map) yerel depolamaya indirilir. İnternet yokken navigasyon çalışmaya devam eder.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                AutoButton(onClick = {}, text = "Bölge Görüntüle", primary = false)
            }
        }

        // Rota hesaplama
        AutoCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Çevrimdışı Rota", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                AutoDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                Text(
                    text = "GraphHopper motoru ile internet olmadan rota hesaplama ve navigasyon rehberliği.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
