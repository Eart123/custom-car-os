package com.customcaros.core.network

import kotlinx.coroutines.flow.Flow

/**
 * Represents the current network connectivity state of the device.
 *
 * The automotive launcher must gracefully handle both online and offline
 * operation. This sealed class is used throughout the app to adapt UI and
 * behavior based on connectivity.
 */
sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
    object Unknown : NetworkState()
}

/**
 * Simple connectivity observer. In production, this should be backed by
 * ConnectivityManager.NetworkCallback. For the initial implementation we
 * expose a suspend-friendly flow that callers can collect.
 */
interface ConnectivityObserver {
    fun observe(): Flow<NetworkState>
}
