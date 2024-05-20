package com.example.maintainease.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


@Database(
    entities = [Maintenance::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(DateRoomConverter::class)
abstract class MaintenanceDB : RoomDatabase() {
    abstract fun maintenanceDAO(): MaintenanceDAO

    fun populateDatabase() {
        val dao = maintenanceDAO()

        val initialMovies = getMaintenance()

        runBlocking(Dispatchers.IO) {
            initialMovies.forEach { movie ->
                val exists = dao.maintenanceExists(movie.title) > 0
                if (!exists) {
                    dao.addMaintenance(movie)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var instance: MaintenanceDB? = null

        fun getDB(context: Context): MaintenanceDB {
            val db = instance ?: synchronized(this) {
                Room.databaseBuilder(context, MaintenanceDB::class.java, "maintenance_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }

            db.populateDatabase()

            return db
        }
    }
}