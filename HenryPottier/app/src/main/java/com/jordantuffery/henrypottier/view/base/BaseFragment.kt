package com.jordantuffery.henrypottier.view.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException
import timber.log.Timber

abstract class BaseFragment : Fragment(), BaseContext by BaseContextImpl() {
    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View = inflater.inflate(layoutRes, container, false)

    override fun onStart() {
        super.onStart()
        Timber.d("${javaClass.simpleName} is started")
        try {
            EventBus.getDefault().register(this)
        } catch (e: EventBusException) {
        }
        restApiServiceConnector.bind(context!!, connectionHandler = this::onRestApiServiceConnected)
    }

    override fun onStop() {
        restApiServiceConnector.unbind(disconnectionHandler = this::onRestApiServiceDisconnected)
        try {
            EventBus.getDefault().unregister(this)
        } catch (e: EventBusException) {
        }
        Timber.d("${javaClass.simpleName} is stopped")
        super.onStop()
    }
}