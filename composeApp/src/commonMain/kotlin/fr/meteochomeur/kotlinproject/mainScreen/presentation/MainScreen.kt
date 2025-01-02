package fr.meteochomeur.kotlinproject.mainScreen.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.MapComponent
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.ModalAddPointForm
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.SearchBar
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.fab_submenu.FabButtonItem
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.fab_submenu.FabButtonMain
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.fab_submenu.FabButtonSub
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.fab_submenu.MultiFloatingActionButton
import fr.meteochomeur.kotlinproject.mainScreen.presentation.component.place_prediction.PredictionList
import org.koin.compose.viewmodel.koinViewModel

enum class FABButton(
    val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    ADD("Add", Icons.Filled.Add), STAR("Star", Icons.Filled.Star), BUILD(
        "Build", Icons.Filled.Build
    )
}

@Composable
fun MainScreenRoot(
    viewModel: MainViewModel = koinViewModel(), modifier: Modifier = Modifier
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current
    Scaffold(floatingActionButton = {
        MultiFloatingActionButton(items = FABButton.entries.map {
            FabButtonItem(
                label = it.label, iconRes = it.icon
            )
        }, onFabItemClicked = { item ->
            when (item.label) {
                FABButton.STAR.label -> {
                    uriHandler.openUri("mailto:blanc.alexispro@gmail.com")
                }

                FABButton.ADD.label -> {
                    viewModel.onEvent(MainScreenEvent.OnOpenModal)
                }

                FABButton.BUILD.label -> {

                }

            }
        }, fabIcon = FabButtonMain(), fabOption = FabButtonSub()
        )
    }) {
        MainScreen(
            uiState = uiState.value, onEvent = viewModel::onEvent, viewmodel = viewModel
        )
    }

}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    uiState: MainState,
    onEvent: (MainScreenEvent) -> Unit,
    viewmodel: MainViewModel
) {


    val keyboardController = androidx.compose.ui.platform.LocalSoftwareKeyboardController.current
    if (uiState.onError) {
        AlertDialog(
            onDismissRequest = {

            },
            confirmButton = {
                Button(onClick = {
                    onEvent(MainScreenEvent.OnAlertDialogClose)
                }) {
                    Text(text = "Ok")
                }
            },
            title = {
                Text(text = "Erreur")
            },
            text = {
                Text(text = uiState.errorMessage)
            },
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color.Blue).statusBarsPadding(),
    ) {
        SearchBar(
            searchQuery = uiState.addressPredictionQuery,
            onSearchQueryChange = {
                onEvent(MainScreenEvent.OnSearchAddressChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        )
        AnimatedVisibility(visible = true) {
            Card(
                modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, bottom = 16.dp),
                elevation = 15.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                PredictionList(predictions = uiState.predictions, onItemClick = {
                    onEvent(MainScreenEvent.OnSearchAddressSelected(it))
                })

            }
        }
        Surface(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            MapComponent(
                commonMarkers = uiState.points2D, callback = viewmodel
            )
            if (uiState.modal.active) {
                FlexibleBottomSheet(
                    onDismissRequest = { onEvent(MainScreenEvent.OnCloseModal) },
                    sheetState = rememberFlexibleBottomSheetState(
                        flexibleSheetSize = FlexibleSheetSize(
                            fullyExpanded = 0.8f,
                            intermediatelyExpanded = 0.65f,
                            slightlyExpanded = 0.15f,
                        ),
                        isModal = true,
                        skipSlightlyExpanded = true,
                    ),
                    containerColor = Color.White,
                ) {
                    ModalAddPointForm(
                        modal = uiState.modal, onEvent = onEvent
                    )
                }
            }
        }
    }


}



