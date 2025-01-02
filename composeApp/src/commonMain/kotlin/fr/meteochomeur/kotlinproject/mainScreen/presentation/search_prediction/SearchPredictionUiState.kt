package fr.meteochomeur.kotlinproject.mainScreen.presentation.search_prediction

import fr.meteochomeur.kotlinproject.mainScreen.domain.GeoCord

data class SearchPredictionUiState(
    val id: String,
    val name: String,
    val context: String,
    val geoCord: GeoCord
)