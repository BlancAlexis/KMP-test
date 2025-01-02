package fr.meteochomeur.kotlinproject.mainScreen.domain

import fr.meteochomeur.kotlinproject.mainScreen.domain.util.DataError
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface PointRepository {
    suspend fun addPoint(point: Point): EmptyResult<DataError.Local>

    suspend fun getPoints(): Flow<List<Point>>

    suspend fun deletePoint(id: String)
}