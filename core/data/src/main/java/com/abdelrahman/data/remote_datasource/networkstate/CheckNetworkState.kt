package com.abdelrahman.data.remote_datasource.networkstate

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

class CheckNetworkState @Inject constructor(
    private val mConnectivityManager: ConnectivityManager
) : ICheckNetworkState {
    override fun isConnection(): Boolean {
        val activeNetwork = mConnectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            mConnectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)

    }
}