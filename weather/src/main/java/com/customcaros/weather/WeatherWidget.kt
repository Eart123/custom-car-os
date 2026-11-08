package com.customcaros.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.customcaros.designsystem.components.AutoCard
import com.customcaros.designsystem.theme.AutomotiveTheme

/**
 * WeatherWidget — premium weather card matching reference dashboard.
 */
@Composable
fun WeatherWidget(
    location: String = "İstanbul",
    temperature: String = "12°",
    condition: String = "Parçalı Bulutlu",
    highLow: String = "High 11° / Low 19°",
    day: String = "Paz",
    date: String = "31 Eki",
    icon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    AutoCard(
        modifier = modifier.fillMaxWidth().height(100.dp),
        elevation = 0.dp,
        backgroundColor = Color(0xFF1A1A1A.toInt())
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = day,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = temperature,
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = "Weather icon",
                        tint = Color(0xFFFFF176.toInt()),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "$condition  •  $highLow",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            icon?.invoke()
        }
    }
}
