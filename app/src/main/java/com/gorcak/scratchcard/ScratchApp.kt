package com.gorcak.scratchcard

import android.app.Application
import com.gorcak.scratchcard.card.data.di.dataModule
import com.gorcak.scratchcard.card.presentation.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class ScratchApp : Application() {


//    val appScope: CoroutineScope = CoroutineScope(SupervisorJob())


    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@ScratchApp)
            modules(
                appModule,
                dataModule
            )
        }
    }
}