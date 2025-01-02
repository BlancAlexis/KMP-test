package fr.meteochomeur.kotlinproject.mainScreen.data.database.repository

import fr.meteochomeur.kotlinproject.mainScreen.data.database.PointDao
import fr.meteochomeur.kotlinproject.mainScreen.data.database.mapper.toEntity
import fr.meteochomeur.kotlinproject.mainScreen.data.database.mapper.toPoint
import fr.meteochomeur.kotlinproject.mainScreen.domain.Point
import fr.meteochomeur.kotlinproject.mainScreen.domain.PointRepository
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.DataError
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.EmptyResult
import fr.meteochomeur.kotlinproject.mainScreen.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PointRepositoryImpl(
    private val pointDao: PointDao
) : PointRepository {
    override suspend fun addPoint(point: Point): EmptyResult<DataError.Local> {
        return try {
            pointDao.addPoint(point.toEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getPoints(): Flow<List<Point>> {

        return pointDao.getPoints().map {
            it.map {
                it.toPoint()
            }
        }

    }

    override suspend fun deletePoint(id: String) {
        pointDao.deletePoint(id)
    }
}

