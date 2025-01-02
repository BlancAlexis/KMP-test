package fr.meteochomeur.kotlinproject.mainScreen.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {
    @Upsert
    fun addPoint(pointEntity: PointEntity)

    @Query("SELECT * FROM PointEntity")
    fun getPoints(): Flow<List<PointEntity>>

    @Query("DELETE FROM PointEntity WHERE id = :id")
    fun deletePoint(id: String)
}