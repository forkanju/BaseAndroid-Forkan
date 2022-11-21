package com.learning.baseprojectforkan.common.app

import android.app.Application
import com.learning.baseprojectforkan.common.di.appModule
import com.learning.baseprojectforkan.common.di.repoModule
import com.learning.baseprojectforkan.common.di.viewModelModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLoggers()
        //  initLifeCykleLog()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }

    }

    //PRETTY_LOGGERS
    private fun initLoggers() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .methodCount(0)
            .methodOffset(5)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    //LifeCykleLog -> Library to easily log out Android lifecycle methods for Activities and Fragments.
    private fun initLifeCykleLog() {
//        LifecykleLog.initialize(this)
//        LifecykleLog.requireAnnotation = false
    }

}