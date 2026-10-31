package com.customcaros.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.Flow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Gerçek cihaz bağlantı durumunu izleyen ConnectivityObserver implementasyonu.
 * ConnectivityManager.NetworkCallback üzerinden Wi-Fi ve mobil veriyi takip eder.
 */
class ConnectivityManagerObserver(private val context: Context) : ConnectivityObserver {

    override fun observe(): Flow<NetworkState> = callbackFlow {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                trySend(NetworkState.Connected)
            }

            override fun onLost(network: android.net.Network) {
                trySend(NetworkState.Disconnected)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        cm.registerNetworkCallback(request, callback)

        awaitClose {
            cm.unregisterNetworkCallback(callback)
        }
    }

    companion object {
        fun init(context: Context): ConnectivityManagerObserver =
            ConnectivityManagerObserver(context.applicationContext)
    }
}

/**
 * Basit synchronized state holder — online/offline bayrağı için.
 */
class ConnectionStateHolder {

    private val _isConnected = java.util.concurrent.atomic.AtomicBoolean(false)
    val isConnected: Boolean get() = _isConnected.get()

    fun setConnected(connected: Boolean) {
        _isConnected.set(connected)
    }
}
