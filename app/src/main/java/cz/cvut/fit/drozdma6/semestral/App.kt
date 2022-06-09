package cz.cvut.fit.drozdma6.semestral

import android.app.Application
import cz.cvut.fit.drozdma6.semestral.features.movies.di.movieModule
import cz.cvut.fit.drozdma6.semestral.shared.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(sharedModule + movieModule)
        }
    }
}
