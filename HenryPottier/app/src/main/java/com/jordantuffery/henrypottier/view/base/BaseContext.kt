package com.jordantuffery.henrypottier.view.base

import android.support.annotation.CallSuper
import com.jordantuffery.henrypottier.RestApiService
import com.jordantuffery.henrypottier.utils.ServiceConnector
import timber.log.Timber

interface BaseContext {
    val restApiServiceConnector: ServiceConnector<RestApiService>
    val restApiService: RestApiService?
        get() = restApiServiceConnector.service

    @CallSuper
    fun onRestApiServiceConnected(restApiService: RestApiService) {
        Timber.d("onRestApiServiceConnected")
    }

    @CallSuper
    fun onRestApiServiceDisconnected(restApiService: RestApiService) {
        Timber.d("onRestApiServiceDisconnected")
    }
}

class BaseContextImpl : BaseContext {
    override val restApiServiceConnector: ServiceConnector<RestApiService> = ServiceConnector(
        RestApiService::class.java)
}