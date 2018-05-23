package com.jordantuffery.henrypottier.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jordantuffery.henrypottier.DataRequestPresenter
import com.jordantuffery.henrypottier.DataRequestPresenterImpl
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {
    abstract val layoutRes: Int

    protected var presenter: DataRequestPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("${javaClass.simpleName} is created")
        setContentView(layoutRes)
        presenter = DataRequestPresenterImpl()
    }

    override fun onStart() {
        super.onStart()
        Timber.d("${javaClass.simpleName} is started")
        try {
            EventBus.getDefault().register(this)
        } catch (e: EventBusException) {
        }
    }

    override fun onStop() {
        try {
            EventBus.getDefault().unregister(this)
        } catch (e: EventBusException) {
        }
        Timber.d("${javaClass.simpleName} is stopped")
        super.onStop()
    }

    override fun onDestroy() {
        presenter = null
        Timber.d("${javaClass.simpleName} is destroyed")
        super.onDestroy()
    }
}