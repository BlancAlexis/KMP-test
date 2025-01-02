package fr.meteochomeur.kotlinproject

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.meteochomeur.kotlinproject.di.initKoin

fun main() = application {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KotlinProject",
        ) {
            App()
        }
    }
}