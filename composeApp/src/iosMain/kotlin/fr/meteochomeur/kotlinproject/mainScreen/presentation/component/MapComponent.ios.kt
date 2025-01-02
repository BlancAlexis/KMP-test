package fr.meteochomeur.kotlinproject.mainScreen.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import fr.meteochomeur.kotlinproject.mainScreen.presentation.CommonMarker
import fr.meteochomeur.kotlinproject.mainScreen.presentation.onMapClickCallback
import fr.meteochomeur.kotlinproject.mapViewController
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapComponent(commonMarkers: List<CommonMarker>, callback: onMapClickCallback) {
    UIKitViewController(
        factory = mapViewController,
        modifier = Modifier.fillMaxSize(),
    )
}