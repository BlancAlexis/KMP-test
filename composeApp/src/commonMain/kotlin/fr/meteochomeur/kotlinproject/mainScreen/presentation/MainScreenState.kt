package fr.meteochomeur.kotlinproject.mainScreen.presentation

import fr.meteochomeur.kotlinproject.mainScreen.domain.Address
import fr.meteochomeur.kotlinproject.mainScreen.domain.GeoCord
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.Modal
import fr.meteochomeur.kotlinproject.mainScreen.presentation.search_prediction.SearchPredictionUiState

data class MainState(
    val modal: Modal = Modal(),
    val addressPredictionQuery: String = "",
    val points2D: List<CommonMarker> = emptyList(),
    val address: Address = Address("", "", GeoCord(0.0, 0.0)),
    val predictions: List<SearchPredictionUiState> = emptyList(),
    val onError: Boolean = false,
    val errorMessage: String = ""
)