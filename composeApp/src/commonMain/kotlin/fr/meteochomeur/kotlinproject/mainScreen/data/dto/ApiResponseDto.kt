package fr.meteochomeur.kotlinproject.mainScreen.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDto(
    val attribution: String? = null,
    val center: List<Double>? = null,
    val features: List<Feature>,
    val licence: String? = null,
    val limit: Int? = null,
    val type: String,
    val version: String? = null,
)
