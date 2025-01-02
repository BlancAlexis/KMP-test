package fr.meteochomeur.kotlinproject.mainScreen.presentation.component.place_prediction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.meteochomeur.kotlinproject.mainScreen.presentation.search_prediction.SearchPredictionUiState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PredictionItem(
    prediction: SearchPredictionUiState,
    onItemClick: (SearchPredictionUiState) -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            onItemClick(prediction)
        }
    ) {
        Icon(
            Icons.Filled.Home,
            contentDescription = null,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(prediction.name)
            Text(prediction.context)
        }
    }
    Divider()
}