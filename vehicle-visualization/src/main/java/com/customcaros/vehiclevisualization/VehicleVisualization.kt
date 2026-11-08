package com.customcaros.vehiclevisualization

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.customcaros.designsystem.components.*
import com.customcaros.designsystem.theme.AutomotiveTheme
import kotlinx.coroutines.CoroutineScope

/**
 * VehicleVisualization — Tesla-inspired araç arayüzü.
 *
 * Model: Renault Symbol
 * Plaka: 34ABC567
 *
 * Şu an 2D Compose tabanlıdır. Filament 3D entegrasyonu için
 * [VehicleVisualization3D] sınıfını kullanabilirsin.
 *
 * ÖNEMLİ: Kapı durumu, yakıt seviyesi, motor sıcaklığı, kilit durumu
 * gibi tüm araç verileri ŞİMDİLİK simülasyondur. Gerçek OBD/CAN
 * arayüzü bağlanmadığı sürece gerçek araç verisi değildir.
 */
@Composable
fun VehicleVisualization(
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    var rotation by remember { mutableStateOf(floatArrayOf(0f, 0f, 0f)) }
    var doorOpen by remember { mutableStateOf(false) }
    var headlightsOn by remember { mutableStateOf(false) }
    var vehicleColor by remember { mutableStateOf(Color(0xFFFFFFFF.toInt())) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.CenterVertically
    ) {
        // Başlık
        Text(
            text = "Renault Symbol",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "34ABC567",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Araç görselleştirme alanı (2D side-profile)
        Box(
            modifier = Modifier
                .size(220.dp, 130.dp)
                .drawBehind {
                    drawRenaultSymbol(
                        rotation = rotation,
                        doorOpen = doorOpen,
                        headlightsOn = headlightsOn,
                        color = vehicleColor
                    )
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Durum göstergeleri
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AutoChip(
                label = if (doorOpen) "Kapı Açık" else "Kapı Kapalı",
                color = if (doorOpen) Color(0xFFFF5252.toInt()) else Color(0xFF00E676.toInt()),
                contentColor = Color(0xFF000000.toInt())
            )
            AutoChip(
                label = if (headlightsOn) "Far Açık" else "Far Kapalı",
                color = if (headlightsOn) Color(0xFFFFF176.toInt()) else Color(0xFF4A4A4A.toInt()),
                contentColor = if (headlightsOn) Color(0xFF000000.toInt()) else Color(0xFFB0B0B0.toInt())
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kontrol butonları
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AutoButton(onClick = { doorOpen = !doorOpen }, text = if (doorOpen) "Kapıyı Kapat" else "Kapıyı Aç", primary = false)
            AutoButton(onClick = { headlightsOn = !headlightsOn }, text = if (headlightsOn) "Farları Kapat" else "Farları Aç", primary = false)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Renk seçici (kısa özet)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Renk:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            ColorChip(selected = vehicleColor == Color(0xFFFFFFFF.toInt()), color = Color.White, onClick = { vehicleColor = Color(0xFFFFFFFF.toInt()) })
            ColorChip(selected = vehicleColor == Color(0xFF000000.toInt()), color = Color.Black, onClick = { vehicleColor = Color(0xFF000000.toInt()) })
            ColorChip(selected = vehicleColor == Color(0xFF00D4FF.toInt()), color = Color(0xFF00D4FF.toInt()), onClick = { vehicleColor = Color(0xFF00D4FF.toInt()) })
            ColorChip(selected = vehicleColor == Color(0xFFFF5252.toInt()), color = Color(0xFFFF5252.toInt()), onClick = { vehicleColor = Color(0xFFFF5252.toInt()) })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Simülasyon uyarısı
        Text(
            text = "⚠ Simülasyon — gerçek OBD/CAN bağlantısı yok",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

/**
 * Renk seçici chip.
 */
@Composable
private fun ColorChip(
    selected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(color, shape = CircleShape)
            .border(
                if (selected) 2.dp else 0.dp,
                MaterialTheme.colorScheme.onSurface,
                CircleShape
            )
            .clickable { onClick() }
    )
}

/**
 * Renault Symbol 2D side-profile çizimi.
 */
private fun DrawScope.drawRenaultSymbol(
    rotation: FloatArray,
    doorOpen: Boolean,
    headlightsOn: Boolean,
    color: Color
) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val carWidth = size.width * 0.82f
    val carHeight = size.height * 0.62f

    rotate(rotation[0], androidx.compose.ui.geometry.Offset(centerX, centerY)) {
        // Karoseri
        drawRect(
            color = color,
            topLeft = androidx.compose.ui.geometry.Offset(centerX - carWidth / 2, centerY - carHeight / 2),
            size = androidx.compose.ui.geometry.Size(carWidth, carHeight)
        )

        // Tekerlekler
        val wheelRadius = carHeight * 0.24f
        drawCircle(
            color = Color.Black,
            center = androidx.compose.ui.geometry.Offset(centerX - carWidth * 0.32f, centerY + carHeight * 0.22f),
            radius = wheelRadius
        )
        drawCircle(
            color = Color.Black,
            center = androidx.compose.ui.geometry.Offset(centerX + carWidth * 0.32f, centerY + carHeight * 0.22f),
            radius = wheelRadius
        )

        // Camlar
        drawRect(
            color = Color(0xFF9CA3AF.toInt()),
            topLeft = androidx.compose.ui.geometry.Offset(centerX - carWidth * 0.28f, centerY - carHeight * 0.3f),
            size = androidx.compose.ui.geometry.Size(carWidth * 0.56f, carHeight * 0.28f)
        )

        // Kapı çizgisi
        if (!doorOpen) {
            drawLine(
                color = Color.Black.copy(alpha = 0.5f),
                start = androidx.compose.ui.geometry.Offset(centerX, centerY - carHeight / 2),
                end = androidx.compose.ui.geometry.Offset(centerX, centerY + carHeight / 2),
                strokeWidth = 2f
            )
        } else {
            drawArc(
                color = Color(0xFFFF5252.toInt()),
                startAngle = -90f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(centerX - carWidth * 0.42f, centerY - carHeight * 0.32f),
                size = androidx.compose.ui.geometry.Size(carWidth * 0.84f, carHeight * 0.64f)
            )
        }

        // Farlar
        if (headlightsOn) {
            drawCircle(
                color = Color(0xFFFFFFFF.toInt()),
                center = androidx.compose.ui.geometry.Offset(centerX - carWidth * 0.38f, centerY - carHeight * 0.05f),
                radius = carHeight * 0.07f
            )
            drawCircle(
                color = Color(0xFFFFFFFF.toInt()),
                center = androidx.compose.ui.geometry.Offset(centerX + carWidth * 0.38f, centerY - carHeight * 0.05f),
                radius = carHeight * 0.07f
            )
        }

        // Plaka (34ABC567) — araç alt kısmına küçük bir çizgi olarak
        drawRect(
            color = Color(0xFFFFFFFF.toInt()),
            topLeft = androidx.compose.ui.geometry.Offset(centerX - carWidth * 0.15f, centerY + carHeight * 0.34f),
            size = androidx.compose.ui.geometry.Size(carWidth * 0.3f, carHeight * 0.06f)
        )
    }
}
