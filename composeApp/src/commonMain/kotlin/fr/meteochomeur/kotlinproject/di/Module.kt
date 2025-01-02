package fr.meteochomeur.kotlinproject.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import fr.meteochomeur.kotlinproject.di.ktor.HttpClientFactory
import fr.meteochomeur.kotlinproject.mainScreen.data.AddressDataSource
import fr.meteochomeur.kotlinproject.mainScreen.data.AddressDataSourceImpl
import fr.meteochomeur.kotlinproject.mainScreen.data.database.builder.DatabaseFactory
import fr.meteochomeur.kotlinproject.mainScreen.data.database.builder.PointDatabase
import fr.meteochomeur.kotlinproject.mainScreen.data.database.repository.PointRepositoryImpl
import fr.meteochomeur.kotlinproject.mainScreen.data.repository.AddressRepositoryImpl
import fr.meteochomeur.kotlinproject.mainScreen.domain.AddressRepository
import fr.meteochomeur.kotlinproject.mainScreen.domain.PointRepository
import fr.meteochomeur.kotlinproject.mainScreen.presentation.MainViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformModule: Module

val sharedModule = module {
    singleOf(::PointRepositoryImpl).bind<PointRepository>()
    singleOf(::AddressRepositoryImpl).bind<AddressRepository>()
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<PointDatabase>().pointDao }
    single { HttpClientFactory.create(get()) }
    singleOf(::AddressDataSourceImpl).bind<AddressDataSource>()
    viewModelOf(::MainViewModel)


}