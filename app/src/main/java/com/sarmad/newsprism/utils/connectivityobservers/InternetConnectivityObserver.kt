package com.sarmad.newsprism.utils.connectivityobservers

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import com.sarmad.newsprism.utils.connectivityobservers.ConnectivityObserver.ConnectionStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class InternetConnectivityObserver @Inject constructor(
    @ApplicationContext context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var internetConnectStatusCallback: ConnectivityManager.NetworkCallback
    private var status: ConnectionStatus = ConnectionStatus.AVAILABLE


    @RequiresApi(Build.VERSION_CODES.N)
    override fun observe(): ConnectionStatus {

            internetConnectStatusCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    status = ConnectionStatus.AVAILABLE

                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    status = ConnectionStatus.LOST
                }

                override fun onUnavailable() {
                    super.onUnavailable()

                    status = ConnectionStatus.UNAVAILABLE
                }
            }
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()
            connectivityManager.registerNetworkCallback(
                networkRequest,
                internetConnectStatusCallback
            )
        return status
    }
}