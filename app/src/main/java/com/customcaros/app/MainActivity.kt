package com.customcaros.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.customcaros.designsystem.theme.AutomotiveTheme
import com.customcaros.dashboard.MainDashboard
import com.customcaros.bluetooth.BluetoothManager
import com.customcaros.core.network.ConnectionStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainActivity : ComponentActivity() {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    private val connectionHolder = ConnectionStateHolder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bluetooth yöneticisini başlat
        BluetoothManager.init(this, scope)

        setContent {
            AutomotiveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CarOsApp(
                        context = this@MainActivity,
                        scope = scope,
                        bluetoothManager = BluetoothManager.get(),
                        connectionHolder = connectionHolder
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BluetoothManager.destroy()
        scope.coroutineContext.cancelChildren()
    }
}

@Composable
fun CarOsApp(
    context: Context,
    scope: CoroutineScope,
    bluetoothManager: BluetoothManager,
    connectionHolder: ConnectionStateHolder
) {
    com.customcaros.dashboard.MainDashboard(
        context = context,
        bluetoothManager = bluetoothManager,
        scope = scope,
        connectionHolder = connectionHolder
    )
}
