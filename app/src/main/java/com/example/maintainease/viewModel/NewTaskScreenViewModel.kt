package com.example.maintainease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maintainease.data.Maintenance
import com.example.maintainease.repositories.MaintenanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class NewTaskScreenViewModel(private val repository: MaintenanceRepository) : ViewModel() {
    private val _maintenances = MutableStateFlow<List<Maintenance>>(emptyList())
    val maintenances: StateFlow<List<Maintenance>> = _maintenances

    init {
        viewModelScope.launch {
            repository.getAllMaintenance()?.collectLatest { maintenanceList ->
                _maintenances.value = maintenanceList
            }
        }
    }

    suspend fun addMaintenance(maintenance: Maintenance){
        viewModelScope.launch {
            repository.addNewMaintenanceTask(maintenance)
        }
    }
}