package fr.meteochomeur.kotlinproject.mainScreen.presentation.component.place_prediction

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.meteochomeur.kotlinproject.mainScreen.presentation.search_prediction.SearchPredictionUiState

@Composable
fun PredictionList(
    predictions: List<SearchPredictionUiState>,
    onItemClick: (SearchPredictionUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(items = predictions.take(5), key = { it.id }) { prediction ->
            PredictionItem(prediction = prediction, onItemClick = onItemClick)
        }
    }
}
