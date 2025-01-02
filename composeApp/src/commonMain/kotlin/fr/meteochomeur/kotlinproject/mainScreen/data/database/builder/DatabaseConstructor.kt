package fr.meteochomeur.kotlinproject.mainScreen.data.database.builder

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object DatabaseConstructor : RoomDatabaseConstructor<PointDatabase> {
    override fun initialize(): PointDatabase
}