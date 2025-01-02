package fr.meteochomeur.kotlinproject.mainScreen.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddressDTO(
    val city: String,
    val citycode: String,
    val context: String, // "01, Ain, Auvergne-Rhône-Alpes"
    val id: String,
    val label: String,
    val name: String,
    val street: String,
    val type: String,
    val x: Double,
    val y: Double
)