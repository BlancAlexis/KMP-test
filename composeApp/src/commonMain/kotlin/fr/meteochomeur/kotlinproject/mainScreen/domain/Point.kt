package fr.meteochomeur.kotlinproject.mainScreen.domain


data class Point(
    val id: Int? = null,
    val title: String = "",
    val coordinates: GeoCord,
    val description: String = "",
    val experience: Int = 0,
    val address: String? = null,
)

data class GeoCord(
    val latitude: Double,
    val longitude: Double
)