package com.jordantuffery.henrypottier.utils

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder

class ServiceConnector<T>(private val serviceClass: Class<T>) where T : Service {
    val onServiceConnection = mutableListOf<(T) -> Unit>()
    val onServiceDisconnection = mutableListOf<(T) -> Unit>()

    var service: T? = null
        private set

    private var ctx: Context? = null

    private val connectionAdapter = ServiceConnectionAdapter()
    private var binding = false

    private val connectionHandlers = mutableListOf<(T) -> Unit>()

    /**
     * Connects to the service represented by this connector.
     *
     * @param context The context in which the service will be running.
     * @param extras Extra parameters given to the service Intent.
     * @param autoCreate Creates the service if it does not exist.
     * @param connectionHandler Callback ran at service connection.
     */
    @JvmOverloads
    fun bind(context: Context, extras: Bundle? = null, autoCreate: Boolean = true, connectionHandler: (T) -> Unit = {}) {
        service?.let {
            connectionHandler(it)
            return
        }

        connectionHandlers.add(connectionHandler)

        if (binding) {
            return
        }

        val intent = Intent(context, serviceClass).apply {
            extras?.let { putExtras(it) }
        }

        ctx = context
        binding = true
        context.bindService(intent, connectionAdapter, if (autoCreate) Context.BIND_AUTO_CREATE else 0)
    }

    /**
     * Disconnects from the service represented by this connector.
     *
     * @param disconnectionHandler Callback ran at service disconnection.
     */
    @JvmOverloads
    fun unbind(disconnectionHandler: (T) -> Unit = {}) {
        val context = ctx ?: return

        service?.let { instance ->
            disconnectionHandler(instance)
            onServiceDisconnection.asReversed().forEach { it(instance) }
        }

        service = null
        context.unbindService(connectionAdapter)
        binding = false
        ctx = null
    }

    private inner class ServiceConnectionAdapter : ServiceConnection {
        @Suppress("UNCHECKED_CAST")
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            if (!binding) {
                return
            }

            val instance = (binder as Binder<T>).service

            binding = false
            service = instance
            onServiceConnection.forEach { it(instance) }

            connectionHandlers.forEach { it(instance) }
            connectionHandlers.clear()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service?.let { instance ->
                onServiceDisconnection.asReversed().forEach { it(instance) }
                service = null
            }
            binding = true
        }
    }

    /**
     * Services must return a binder implementing this interface in their onBind method
     * in order to be accessed with a connector.
     *
     * @param T The type of the service.
     * @property service The current instance of the service.
     */
    interface Binder<out T : Service> {
        val service: T
    }
}
