package com.customcaros.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.customcaros.designsystem.theme.AutomotiveColors

/**
 * Dashboard status bar — time, date, internet/Bluetooth indicator and battery.
 * Tesla tarzı ince ve dokunmatik dostu üst çubuk.
 */
@Composable
fun AutoTopBar(
    time: String,
    date: String,
    internetStatus: Boolean,
    bluetoothStatus: Boolean,
    batteryLevel: Int,
    modifier: Modifier = Modifier
) {
    AutoCard(
        modifier = modifier.fillMaxWidth().height(56.dp),
        elevation = 0.dp,
        backgroundColor = AutomotiveColors.BackgroundSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.titleLarge,
                color = AutomotiveColors.TextPrimary
            )
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
                color = AutomotiveColors.TextSecondary
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatusDot(label = "Internet", active = internetStatus)
                StatusDot(label = "Bluetooth", active = bluetoothStatus)
                Text(
                    text = "$batteryLevel%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AutomotiveColors.TextSecondary
                )
            }
        }
    }
}

@Composable
private fun StatusDot(label: String, active: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(if (active) AutomotiveColors.Success else AutomotiveColors.Error)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = AutomotiveColors.TextSecondary
        )
    }
}
