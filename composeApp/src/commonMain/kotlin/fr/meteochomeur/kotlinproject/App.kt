package fr.meteochomeur.kotlinproject

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import fr.meteochomeur.kotlinproject.mainScreen.presentation.MainScreenRoot
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = Routes.MainGraph) {
            navigation<Routes.MainGraph>(startDestination = Routes.MainScreen) {
                composable<Routes.MainScreen> {
                    MainScreenRoot(koinViewModel())
                }
                composable<Routes.AddScreen> {

                }
            }
        }

    }
}

sealed interface Routes {
    @Serializable
    data object MainGraph : Routes

    @Serializable
    data object MainScreen : Routes

    @Serializable
    data class AddScreen(val bookId: String) : Routes
}