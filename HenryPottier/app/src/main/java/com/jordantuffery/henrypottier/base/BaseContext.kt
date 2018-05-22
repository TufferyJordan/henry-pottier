package com.jordantuffery.henrypottier.base

import android.support.annotation.CallSuper
import com.jordantuffery.henrypottier.DataRequestService
import com.jordantuffery.henrypottier.utils.ServiceConnector
import timber.log.Timber

interface BaseContext {
    val dataRequestServiceConnector: ServiceConnector<DataRequestService>
    val dataRequestService: DataRequestService?
        get() = dataRequestServiceConnector.service

    @CallSuper
    fun onDataRequestServiceConnected(dataRequestService: DataRequestService) {
        Timber.d("onDataRequestServiceConnected")
    }

    @CallSuper
    fun onDataRequestServiceDisconnected(dataRequestService: DataRequestService) {
        Timber.d("onDataRequestServiceDisconnected")
    }
}

class BaseContextImpl : BaseContext {
    override val dataRequestServiceConnector: ServiceConnector<DataRequestService> = ServiceConnector(
        DataRequestService::class.java)
}