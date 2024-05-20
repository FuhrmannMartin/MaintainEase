package com.example.maintainease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maintainease.data.Maintenance
import com.example.maintainease.repositories.MaintenanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val repository: MaintenanceRepository, taskId: Int) : ViewModel() {
    private val _maintenance = MutableStateFlow<Maintenance?>(null)
    val maintenance: StateFlow<Maintenance?> = _maintenance

    init {
        viewModelScope.launch {
            repository.getAllMaintenance().collectLatest { maintenanceList ->
                val maintenance = maintenanceList.find { it.id == taskId }
                _maintenance.value = maintenance
            }
        }
    }
}
