package fr.meteochomeur.kotlinproject.mainScreen.domain

data class Address(
    val rue: String,
    val infoComp: String,
    val geoCord: GeoCord
)