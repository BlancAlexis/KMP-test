package fr.meteochomeur.kotlinproject.mainScreen.presentation

import fr.meteochomeur.kotlinproject.mainScreen.presentation.search_prediction.SearchPredictionUiState

sealed interface MainScreenEvent {
    data object OnOpenModal : MainScreenEvent
    data object OnCloseModal : MainScreenEvent
    data class OnSearchAddressChange(val query: String) : MainScreenEvent
    data class OnSearchAddressSelected(val query: SearchPredictionUiState) : MainScreenEvent
    data class OnTitleChange(val title: String) : MainScreenEvent
    data class OnDescriptionChange(val description: String) : MainScreenEvent
    data class OnStarsChange(val stars: Int) : MainScreenEvent
    object OnSave : MainScreenEvent
    object OnCancel : MainScreenEvent
    object OnSelectLocation : MainScreenEvent
    object OnAlertDialogClose : MainScreenEvent
}