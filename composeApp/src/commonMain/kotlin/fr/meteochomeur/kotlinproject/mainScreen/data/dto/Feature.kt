package fr.meteochomeur.kotlinproject.mainScreen.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)

