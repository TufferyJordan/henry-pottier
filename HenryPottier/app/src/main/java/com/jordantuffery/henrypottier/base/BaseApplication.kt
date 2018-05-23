package com.jordantuffery.henrypottier.base

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.jordantuffery.henrypottier.BuildConfig
import com.jordantuffery.henrypottier.utils.ConnectivityChangeEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException
import timber.log.Timber


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            EventBus.builder()
                .eventInheritance(false)
                .logNoSubscriberMessages(false)
                .installDefaultEventBus()
        } catch (ignored: EventBusException) {
            Timber.e(ignored)
        }

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()

        connectivityManager.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    EventBus.getDefault().postSticky(ConnectivityChangeEvent(true))
                }

                override fun onLost(network: Network) {
                    EventBus.getDefault().postSticky(ConnectivityChangeEvent(false))
                }
            }
        )
    }

    override fun onTerminate() {
        EventBus.getDefault().removeAllStickyEvents()
        super.onTerminate()
    }
}

