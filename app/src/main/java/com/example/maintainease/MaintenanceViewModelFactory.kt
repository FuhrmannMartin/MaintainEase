package com.example.maintainease

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.maintainease.repositories.MaintenanceRepository
import com.example.maintainease.viewModel.OverviewScreenViewModel

class OverviewScreenViewModelFactory(private val repository: MaintenanceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(OverviewScreenViewModel::class.java) -> {
                OverviewScreenViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}