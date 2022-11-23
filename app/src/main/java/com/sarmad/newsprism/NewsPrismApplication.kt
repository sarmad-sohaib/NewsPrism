package com.sarmad.newsprism

import android.app.Application
import com.sarmad.newsprism.utils.connectivityobservers.ConnectivityObserver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NewsPrismApplication: Application() {

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate() {
        super.onCreate()
//        connectivityObserver.observe()
    }
}