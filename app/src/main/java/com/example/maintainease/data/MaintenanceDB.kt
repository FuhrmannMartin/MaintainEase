package com.example.maintainease.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.maintainease.repositories.MaintenanceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [Maintenance::class, Team::class, Staff::class,
        TeamMaintenanceRelation::class, TeamStaffRelation::class, StaffMaintenanceRelation::class],
    version = 4,
    exportSchema = false
)

@TypeConverters(DateRoomConverter::class)
abstract class MaintenanceDB : RoomDatabase() {
    abstract fun maintenanceDAO(): MaintenanceDAO
    abstract fun staffDAO(): StaffDAO
    abstract fun teamDAO(): TeamDAO

    companion object {
        @Volatile
        private var Instance: MaintenanceDB? = null

        fun getDB(context: Context): MaintenanceDB {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MaintenanceDB::class.java, "maintenance_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(context.applicationContext))
                    .build()
                    .also { Instance = it }
            }
        }
    }

    private class DatabaseCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                // Populate the database with initial data in the background
                val maintenanceDAO = getDB(context).maintenanceDAO()
                val staffDAO = getDB(context).staffDAO()
                val teamDAO = getDB(context).teamDAO()

                val initialMaintenanceTasks = getMaintenance()
                val initialStaff = getStaff()
                val initialTeams = getTeam()

                val maintenanceRepository = MaintenanceRepository(maintenanceDAO, staffDAO)
                initialMaintenanceTasks.forEach { o ->
                    maintenanceRepository.addMaintenanceWithRelation(o)
                }
                initialStaff.forEach { o ->
                    staffDAO.addStaffMember(o)
                }
                initialTeams.forEach { o ->
                    teamDAO.addTeam(o)
                }
            }
        }
    }
}