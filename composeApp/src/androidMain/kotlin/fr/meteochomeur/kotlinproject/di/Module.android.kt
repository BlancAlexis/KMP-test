package fr.meteochomeur.kotlinproject.di

import fr.meteochomeur.kotlinproject.mainScreen.data.database.builder.DatabaseFactory
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { OkHttp.create() }
        single {
            DatabaseFactory(
                androidApplication()
            )
        }

    }