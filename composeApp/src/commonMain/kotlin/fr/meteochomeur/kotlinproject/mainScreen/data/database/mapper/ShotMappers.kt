package fr.meteochomeur.kotlinproject.mainScreen.data.database.mapper

import fr.meteochomeur.kotlinproject.mainScreen.data.database.PointEntity
import fr.meteochomeur.kotlinproject.mainScreen.domain.GeoCord
import fr.meteochomeur.kotlinproject.mainScreen.domain.Point

fun Point.toEntity() = PointEntity(
    title = title,
    latitude = coordinates.latitude,
    longitude = coordinates.longitude,
    description = description,
    experience = experience,
    address = address,
    id = id
)

fun PointEntity.toPoint() = Point(
    title = title,
    coordinates = GeoCord(latitude, longitude),
    experience = experience,
    description = description,
    address = address

)