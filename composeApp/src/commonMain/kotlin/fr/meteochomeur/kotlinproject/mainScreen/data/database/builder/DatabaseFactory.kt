package fr.meteochomeur.kotlinproject.mainScreen.data.database.builder

import androidx.room.RoomDatabase


expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<PointDatabase>
}