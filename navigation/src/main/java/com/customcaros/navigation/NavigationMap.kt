package com.customcaros.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
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
 * NavigationMap — split-screen map panel matching reference dashboard.
 * Shows map with speed overlay, navigation controls, and weather widget.
 */
@Composable
fun NavigationMap(
    speedKmh: String = "0",
    modifier: Modifier = Modifier
) {
    var zoomPlus by remember { mutableStateOf(false) }
    var zoomMinus by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        // Map background area (placeholder for Mapsforge)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF0F0F0F.toInt()))
        ) {
            // Speed display (top left of map)
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AutoLargeNumber(value = speedKmh, fontSize = 32.dp, color = Color.White)
                AutoUnitLabel(text = "km/h", color = Color(0xFFB0B0B0.toInt()))
            }

            // Navigation arrow button (below speed)
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp)
                    .offset(y = 90.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(48.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFF2A2A2A.toInt()),
                        contentColor = Color.White
                    ),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Navigation,
                        contentDescription = "Navigation",
                        tint = Color.White
                    )
                }
            }

            // Zoom controls (right side of map)
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { zoomPlus = true },
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFF2A2A2A.toInt()),
                        contentColor = Color.White
                    ),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Zoom in",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = { zoomMinus = true },
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFF2A2A2A.toInt()),
                        contentColor = Color.White
                    ),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Zoom out",
                        tint = Color.White
                    )
                }
            }
        }

        // Bottom action bar on map (weather + quick actions)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF1A1A1A.toInt()))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Quick action chips
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionChip(
                    icon = { Icon(Icons.Default.Mic, contentDescription = "Voice", tint = Color.White) },
                    label = "Ses",
                    onClick = {}
                )
                ActionChip(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) },
                    label = "Ev",
                    onClick = {}
                )
                ActionChip(
                    icon = { Icon(Icons.Default.LocalGasStation, contentDescription = "Fuel", tint = Color.White) },
                    label = "Yakıt",
                    onClick = {}
                )
                ActionChip(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White) },
                    label = "Ara",
                    onClick = {}
                )
            }

            // Weather widget
            com.customcaros.weather.WeatherWidget(
                modifier = Modifier.weight(1f).padding(start = 16.dp),
                location = "İstanbul",
                temperature = "12°",
                condition = "Parçalı Bulutlu",
                highLow = "High 11° / Low 19°",
                day = "Paz",
                date = "31 Eki",
                icon = {
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = "Weather",
                            tint = Color(0xFFFFF176.toInt())
                        )
                    }
                }
            )
        }
    }
}

/**
 * Small action chip for map bottom bar.
 */
@Composable
private fun ActionChip(
    icon: @Composable (() -> Unit)?,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(64.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF2A2A2A.toInt()))
        ) {
            icon?.invoke()
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = Color(0xFFB0B0B0.toInt()),
            maxLines = 1
        )
    }
}
