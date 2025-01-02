package fr.meteochomeur.kotlinproject.mainScreen.data.database.builder

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import fr.meteochomeur.kotlinproject.mainScreen.data.database.PointDao
import fr.meteochomeur.kotlinproject.mainScreen.data.database.PointEntity


@Database(
    entities = [PointEntity::class],
    version = 1
)
@ConstructedBy(DatabaseConstructor::class)
abstract class PointDatabase : RoomDatabase() {
    abstract val pointDao: PointDao

    companion object {
        const val DB_NAME = "point.db"
    }
}