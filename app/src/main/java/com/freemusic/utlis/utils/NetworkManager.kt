package com.freemusic.utlis.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkManager constructor(mContext: Context?) {

    var mContext: Context? = null

    init {
        this.mContext = mContext!!
    }

    /* class for checking Internet connectivity */
    fun checkInternetConnection(): Boolean {
        if (mContext == null) return false
        var con_manager: ConnectivityManager =
            mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return con_manager.activeNetworkInfo != null && con_manager.activeNetworkInfo.isConnected

    }
}