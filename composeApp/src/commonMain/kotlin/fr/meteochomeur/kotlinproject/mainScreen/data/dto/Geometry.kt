package fr.meteochomeur.kotlinproject.mainScreen.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val coordinates: List<Double>,
    val type: String
)