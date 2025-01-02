package fr.meteochomeur.kotlinproject.mainScreen.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PointEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val experience: Int,
    val address: String?,
    val description: String,
)