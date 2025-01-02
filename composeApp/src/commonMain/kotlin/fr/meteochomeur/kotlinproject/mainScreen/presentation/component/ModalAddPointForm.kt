package fr.meteochomeur.kotlinproject.mainScreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import fr.meteochomeur.kotlinproject.mainScreen.presentation.MainScreenEvent
import fr.meteochomeur.kotlinproject.mainScreen.presentation.search_prediction.SearchPredictionUiState

data class Modal(
    val active: Boolean = false,
    val titre: String = "",
    val description: String? = null,
    val prediction: SearchPredictionUiState? = null,
    val stars: Int = 0
) {
    fun formattedAddress(): String {
        return prediction?.name + ", " + prediction?.context
    }
}


@Composable
fun ModalAddPointForm(
    modal: Modal,
    onEvent: (MainScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier.background(Color.White).wrapContentSize().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            textStyle = androidx.compose.ui.text.TextStyle(),
            singleLine = true,
            value = modal.titre,
            onValueChange = {
                onEvent(MainScreenEvent.OnTitleChange(it))
            },
            shape = RoundedCornerShape(25),
            placeholder = {
                Text(text = "Titre")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                readOnly = modal.prediction != null,
                enabled = modal.prediction != null,
                singleLine = true,
                value = modal.prediction?.name ?: "",
                onValueChange = {
                },
                shape = RoundedCornerShape(25),
                placeholder = {
                    Text(text = "Location")
                },
                modifier = Modifier.weight(1f)
            )
            Button(
                enabled = false,
                onClick = { onEvent(MainScreenEvent.OnSelectLocation) },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                )
            }
        }

        OutlinedTextField(
            singleLine = false,
            minLines = 5,
            value = modal.description ?: "",
            onValueChange = {
                onEvent(MainScreenEvent.OnDescriptionChange(it))
            },
            shape = RoundedCornerShape(12),
            placeholder = {
                Text(text = "Description")
            },
            modifier = Modifier.fillMaxWidth().padding(end = 32.dp)
        )
        IconsRow(nbIcons = 5, onIconSelectedIndex = {
            onEvent(MainScreenEvent.OnStarsChange(it))
        })
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onEvent(MainScreenEvent.OnCancel) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
            ) {
                Text("no")
            }
            Button(onClick = { onEvent(MainScreenEvent.OnSave) }) {
                Text("ok")
            }

        }

    }
}

@Composable
fun IconsRow(
    iconVector: ImageVector = Icons.Default.Star,
    nbIcons: Int = 5,
    onIconSelectedIndex: (Int) -> Unit
) {
    val lastIconsSelectedIndex = remember { mutableIntStateOf(-1) }
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        (1..nbIcons).forEachIndexed { index, _ ->
            val interactionSource = remember { MutableInteractionSource() }
            val pressed = interactionSource.collectIsPressedAsState()
            val hovered = interactionSource.collectIsHoveredAsState()
            LaunchedEffect(key1 = hovered.value, key2 = pressed.value) {
                if (hovered.value || pressed.value) {
                    lastIconsSelectedIndex.value = index
                }
            }
            IconButton(
                interactionSource = interactionSource,
                onClick = { onIconSelectedIndex(index + 1) },
                modifier = Modifier.padding(horizontal = 4.dp).background(
                    color = Color.Gray, shape = RoundedCornerShape(50)
                )
            ) {
                Icon(
                    tint = if (lastIconsSelectedIndex.intValue != -1 && lastIconsSelectedIndex.intValue >= index) Color.Yellow else Color.Black,
                    imageVector = iconVector,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp)
                )
            }
        }
    }
}