package com.example.maintainease.repositories

import android.util.Log
import com.example.maintainease.data.Maintenance
import com.example.maintainease.data.MaintenanceDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class MaintenanceRepository(private val maintenanceDAO: MaintenanceDAO) {
    suspend fun getAllMaintenance(): Flow<List<Maintenance>> {
        return flow {
            val moviesWithImages = maintenanceDAO.getAllMaintenance()
            Log.d("MaintenanceRepository", "Fetched ${moviesWithImages.size} maintenance tasks")
            emit(moviesWithImages)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun update(maintenances: List<Maintenance>) {
        withContext(Dispatchers.IO) {
            maintenanceDAO.updateMaintenance(maintenances)
        }
    }

    companion object {
        @Volatile
        private var instance: MaintenanceRepository? = null

        fun getInstance(dao: MaintenanceDAO): MaintenanceRepository {
            return instance ?: synchronized(this) {
                instance ?: MaintenanceRepository(dao).also { instance = it }
            }
        }
    }
}