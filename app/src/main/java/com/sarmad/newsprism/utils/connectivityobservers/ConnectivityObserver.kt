package com.sarmad.newsprism.utils.connectivityobservers

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): ConnectionStatus

    enum class ConnectionStatus {
        AVAILABLE,
        UNAVAILABLE,
        LOST
    }
}