package fr.meteochomeur.kotlinproject

import androidx.compose.ui.window.ComposeUIViewController
import fr.meteochomeur.kotlinproject.di.initKoin

fun MainViewController(mapUIViewController: () -> UIViewController) =
    ComposeUIViewController(configure = { initKoin() }) {
        mapViewController = mapUIViewController
        App()
    }

lateinit var mapViewController: () -> UIViewController