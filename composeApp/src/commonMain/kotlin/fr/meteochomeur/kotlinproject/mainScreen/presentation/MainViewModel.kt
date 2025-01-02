package fr.meteochomeur.kotlinproject.mainScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.meteochomeur.kotlinproject.mainScreen.domain.Address
import fr.meteochomeur.kotlinproject.mainScreen.domain.AddressRepository
import fr.meteochomeur.kotlinproject.mainScreen.domain.Point
import fr.meteochomeur.kotlinproject.mainScreen.domain.PointRepository
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.onError
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.onSuccess
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.Modal
import fr.meteochomeur.kotlinproject.mainScreen.presentation.search_prediction.SearchPredictionUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val pointRepository: PointRepository, private val addressRepository: AddressRepository
) : ViewModel(), onMapClickCallback {

    private val _state = MutableStateFlow(MainState())
    val state = _state.onStart {
        loadAllPoint()
        observeAutoCompletionQuery()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainState()
    )

    private var predictionsJob: Job? = null


    private fun insertPoint(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            pointRepository.addPoint(point).onSuccess {
                println("succes")
            }.onError {
                onRequestError(it.name)
            }
        }
    }

    private fun loadAllPoint() {
        viewModelScope.launch(Dispatchers.IO) {
            pointRepository.getPoints().collect { point ->
                _state.update {
                    it.copy(points2D = state.value.points2D + point.map {
                        CommonMarker(
                            lat = it.coordinates.latitude,
                            long = it.coordinates.longitude,
                            name = it.title,
                            description = it.description
                        )
                    })

                }
            }
        }
    }

    private fun observeAutoCompletionQuery() {
        state.map { it.addressPredictionQuery }.distinctUntilChanged().debounce(500L)
            .onEach { query ->
                if (query.length < 4) {
                    return@onEach
                }
                if (predictionsJob?.isActive == true) {
                    predictionsJob?.cancel()
                }
                predictionsJob = getPredictionsJob(query)
            }.launchIn(viewModelScope)
    }

    private fun getPredictionsJob(query: String): Job {
        return viewModelScope.launch {
            addressRepository.getAddressesByQuery(query).onSuccess { addresses ->
                updatePredictions(addresses.map {
                    it.toPredictionUiState()
                }.toList())
            }.onError {
                onRequestError(it.name)
            }
        }
    }

    private fun updatePredictions(predictions: List<SearchPredictionUiState>) {
        _state.update {
            it.copy(predictions = predictions)

        }
    }

    private fun onRequestError(errorMessage: String) {
        _state.update {
            it.copy(onError = true, errorMessage = errorMessage)
        }
    }

    override fun onMapClick(lat: Double, lon: Double) {
        viewModelScope.launch {
            addressRepository.getAddressesByCoordinates(
                lat, lon
            ).onSuccess { response ->
                if (response.isEmpty()) {
                    onRequestError("aucune adresse trouver")
                    return@onSuccess
                }
                _state.update {
                    it.copy(
                        modal = Modal(
                            active = true, prediction = response.first().toPredictionUiState()
                        )
                    )
                }
            }.onError {
                onRequestError(it.name)
            }
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            MainScreenEvent.OnCancel -> {
                _state.update {
                    it.copy(modal = Modal(), predictions = emptyList())
                }
            }

            is MainScreenEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(modal = _state.value.modal.copy(description = event.description))
                }
            }

            MainScreenEvent.OnSave -> {
                _state.value.modal.prediction?.let {
                    viewModelScope.launch(Dispatchers.IO) {
                        insertPoint(
                            Point(
                                title = _state.value.modal.titre,
                                description = _state.value.modal.description ?: "",
                                experience = _state.value.modal.stars,
                                coordinates = it.geoCord
                            )
                        )
                    }
                    _state.update {
                        it.copy(modal = Modal(), predictions = emptyList())
                    }
                }


            }

            MainScreenEvent.OnSelectLocation -> {

            }

            is MainScreenEvent.OnStarsChange -> {
                _state.update {
                    it.copy(modal = _state.value.modal.copy(stars = event.stars))
                }
            }

            is MainScreenEvent.OnTitleChange -> {
                _state.update {
                    it.copy(modal = _state.value.modal.copy(titre = event.title))
                }
            }

            is MainScreenEvent.OnSearchAddressChange -> {
                _state.update {
                    it.copy(addressPredictionQuery = event.query)
                }
            }

            is MainScreenEvent.OnSearchAddressSelected -> {
                _state.update {
                    it.copy(modal = Modal(active = true, prediction = event.query))
                }
            }

            MainScreenEvent.OnOpenModal -> _state.update {
                it.copy(modal = Modal(active = true))
            }

            MainScreenEvent.OnCloseModal -> _state.update {
                it.copy(modal = Modal(active = false), predictions = emptyList())
            }

            MainScreenEvent.OnAlertDialogClose -> _state.update {
                it.copy(onError = false, errorMessage = "")
            }
        }
    }
}

fun Address.toPredictionUiState() = SearchPredictionUiState(
    id = this.rue,
    name = this.rue,
    context = this.infoComp,
    geoCord = this.geoCord
)


interface onMapClickCallback {
    fun onMapClick(lat: Double, lon: Double)
}

