package com.jordantuffery.henrypottier.view.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jordantuffery.henrypottier.RestApiService
import com.jordantuffery.henrypottier.utils.ServiceConnector
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException
import timber.log.Timber

abstract class BaseFragment : Fragment() {
    val restApiServiceConnector: ServiceConnector<RestApiService> = ServiceConnector(RestApiService::class.java)
    val restApiService: RestApiService?
        get() = restApiServiceConnector.service

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

    @CallSuper
    open fun onRestApiServiceConnected(restApiService: RestApiService) {
        Timber.d("onRestApiServiceConnected")
    }

    @CallSuper
    open fun onRestApiServiceDisconnected(restApiService: RestApiService) {
        Timber.d("onRestApiServiceDisconnected")
    }
}