package fr.meteochomeur.kotlinproject.mainScreen.presentation.component

import androidx.compose.runtime.Composable
import fr.meteochomeur.kotlinproject.mainScreen.presentation.CommonMarker
import fr.meteochomeur.kotlinproject.mainScreen.presentation.onMapClickCallback

@Composable
expect fun MapComponent(commonMarkers: List<CommonMarker>, callback: onMapClickCallback)