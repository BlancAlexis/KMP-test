package fr.meteochomeur.kotlinproject

import android.app.Application
import fr.meteochomeur.kotlinproject.di.initKoin
import org.koin.android.ext.koin.androidContext

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AppApplication)
        }
    }
}