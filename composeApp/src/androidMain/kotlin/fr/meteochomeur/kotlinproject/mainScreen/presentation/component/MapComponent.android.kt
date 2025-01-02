package fr.meteochomeur.kotlinproject.mainScreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import fr.meteochomeur.kotlinproject.mainScreen.presentation.CommonMarker
import fr.meteochomeur.kotlinproject.mainScreen.presentation.onMapClickCallback

@OptIn(ExperimentalLayoutApi::class)
@Composable
actual fun MapComponent(commonMarkers: List<CommonMarker>, callback: onMapClickCallback) {
    // val info by rememberSaveable { mutableStateOf<Boolean>()}

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(46.2276, 2.2137), 5f)
        }
        GoogleMap(
            uiSettings = MapUiSettings(zoomControlsEnabled = false),
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                callback.onMapClick(latLng.latitude, latLng.longitude)
            }
        ) {

            commonMarkers.forEach { marker ->
                MarkerInfoWindow(

                    state = rememberMarkerState(position = LatLng(marker.long, marker.lat)),
                ) {
                    Card {
                        Column(
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                        ) {
                            Text(text = marker.name)
                            FlowRow {
                                (1..5).forEachIndexed { index, _ ->
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = if (index <= marker.stars) Color.Yellow else Color.Gray
                                    )
                                }
                            }

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun CustomMapMarker(
    imageUrl: String?,
    fullName: String,
    location: LatLng,
    onClick: () -> Unit
) {
    val markerState = remember { MarkerState(position = location) }
    val shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 0.dp)


    MarkerComposable(
        keys = arrayOf(fullName),
        state = markerState,
        title = fullName,
        anchor = Offset(0.5f, 1f),
        onClick = {
            onClick()
            true
        }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(shape)
                .background(Color.LightGray)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            //if (!imageUrl.isNullOrEmpty()) {


            //   } else {
            //       Text(
            //            text = fullName.take(1).uppercase(),
            //           color = Color.White,
            //          style = MaterialTheme.typography.body2,
            //          modifier = Modifier.align(Alignment.Center)
            //     )
            //  }
        }
    }
}