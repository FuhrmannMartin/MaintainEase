package com.example.maintainease.data

import android.content.Context
import com.example.maintainease.NewTaskScreenViewModelFactory
import com.example.maintainease.OverviewScreenViewModelFactory
import com.example.maintainease.repositories.MaintenanceRepository

object InjectorUtils {
    private fun getMovieRepository(context: Context): MaintenanceRepository {
        val db = MaintenanceDB.getDB(context)
        return MaintenanceRepository.getInstance(db.maintenanceDAO())
    }

    fun provideOverviewScreenViewModelFactory(context: Context): OverviewScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return OverviewScreenViewModelFactory(repository)
    }

    fun provideNewTaskScreenViewModelFactory(context: Context): NewTaskScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return NewTaskScreenViewModelFactory(repository)
    }

}