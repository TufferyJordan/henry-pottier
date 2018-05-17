package com.jordantuffery.henrypottier.lifecyle.base

import android.app.Application
import com.jordantuffery.henrypottier.BuildConfig
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
    }

    override fun onTerminate() {
        EventBus.getDefault().removeAllStickyEvents()
        super.onTerminate()
    }
}