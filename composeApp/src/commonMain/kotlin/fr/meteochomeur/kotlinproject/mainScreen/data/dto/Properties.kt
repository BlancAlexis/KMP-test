package fr.meteochomeur.kotlinproject.mainScreen.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Properties(
    val city: String,
    val context: String,
    val distance: Int? = null,
    val housenumber: String? = null,
    val id: String,
    val label: String,
    val name: String,
    val street: String?= null,
    val type: String
)