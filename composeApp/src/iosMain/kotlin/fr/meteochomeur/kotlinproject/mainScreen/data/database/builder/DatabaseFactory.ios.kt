package fr.meteochomeur.kotlinproject.mainScreen.data.database.builder

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<PointDatabase> {
        val dbFile = documentDirectory() + "/${PointDatabase.Companion.DB_NAME}"
        return Room.databaseBuilder<PointDatabase>(
            name = dbFile
        )
    }

    private documentDirectory(): String
    {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = true,
            error = null
        )
        return requireNotNull(documentDirectory?.path)
    }
}