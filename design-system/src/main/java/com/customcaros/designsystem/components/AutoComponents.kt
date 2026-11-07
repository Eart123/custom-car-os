package com.customcaros.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.FontFamily
import androidx.compose.ui.text.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AutomotiveShape {
    val Small = 8.dp
    val Medium = 12.dp
    val Large = 16.dp
    val XLarge = 20.dp
    val Full = 50.dp
}

/**
 * Automotive card — elevated surface with rounded corners, used for dashboard widgets.
 */
@Composable
fun AutoCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        content = content
    )
}

/**
 * Large touch-target button designed for use while driving.
 * Minimum 48dp touch target.
 */
@Composable
fun AutoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    icon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    primary: Boolean = true
) {
    val colors = if (primary) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = colors,
        shape = RoundedCornerShape(14.dp),
        enabled = enabled,
        contentPadding = PaddingValues(16.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.invoke()
            if (icon != null) Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = if (primary) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Toggle switch for settings, styled for automotive use.
 */
@Composable
fun AutoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.height(32.dp),
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.primary,
            checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            uncheckedThumbColor = MaterialTheme.colorScheme.outline,
            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}

/**
 * Info chip — small status indicator (Bluetooth, GPS, battery, etc.)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoChip(
    label: String,
    icon: @Composable (() -> Unit)? = null,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.primary
) {
    AssistChip(
        onClick = { },
        modifier = Modifier.height(36.dp),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor
            )
        },
        leadingIcon = icon,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = color,
            labelColor = contentColor,
            leadingIconContentColor = contentColor
        ),
        shape = RoundedCornerShape(AutomotiveShape.Full)
    )
}

/**
 * Status row — label on left, value/status on right, with optional icon.
 */
@Composable
fun AutoStatusRow(
    label: String,
    value: String,
    icon: @Composable (() -> Unit)? = null,
    valueColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.invoke()
            if (icon != null) Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            color = valueColor
        )
    }
}

/**
 * Divider line for separating dashboard sections.
 */
@Composable
fun AutoDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
        thickness = 1.dp
    )
}

/**
 * Glow effect container — used around the 3D vehicle visualization
 * to give a Tesla-style premium feel.
 */
@Composable
fun AutoGlowContainer(
    modifier: Modifier = Modifier,
    glowColor: Color = Color(0xFF00D4FF.toInt()),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = glowColor.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        content()
    }
}

/**
 * Large numeric display for speed, time, etc. Monospace for stability.
 */
@Composable
fun AutoLargeNumber(
    value: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: Dp = 48.dp
) {
    Text(
        text = value,
        style = MaterialTheme.typography.displayLarge.copy(
            fontSize = fontSize.value.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        ),
        color = color
    )
}

/**
 * Small label under a large number (e.g., "km/h", "°C").
 */
@Composable
fun AutoUnitLabel(
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = color
    )
}

/**
 * Dashboard app dock item — large touch target for bottom bar.
 */
@Composable
fun AutoDockItem(
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)?,
    label: String,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = modifier
            .size(72.dp, 72.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
        ) {
            icon?.invoke()
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            ),
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(48.dp)
        )
    }
}

/**
 * Bottom app dock — Tesla-style app bar with large touch targets.
 */
@Composable
fun AutoAppDock(
    items: List<DockItem>,
    selectedIndex: Int,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth().height(88.dp)
    ) {
        // Top divider
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Top),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            thickness = 1.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .align(Alignment.Bottom),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                AutoDockItem(
                    onClick = { onItemClick(index) },
                    icon = item.icon,
                    label = item.label,
                    isSelected = selectedIndex == index,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

data class DockItem(
    val icon: @Composable (() -> Unit)?,
    val label: String
)
