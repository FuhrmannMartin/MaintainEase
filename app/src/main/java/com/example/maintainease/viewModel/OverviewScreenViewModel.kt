package com.example.maintainease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maintainease.data.Maintenance
import com.example.maintainease.repositories.MaintenanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OverviewScreenViewModel(private val repository: MaintenanceRepository) : ViewModel() {
    private val _maintenances = MutableStateFlow<List<Maintenance>>(emptyList())

    init {
        viewModelScope.launch {
            repository.getAllMaintenance().collectLatest { maintenanceList ->
                _maintenances.value = maintenanceList
            }
        }
    }


    val openMaintenances: StateFlow<List<Maintenance>> = _maintenances.map { maintenances ->
        maintenances.filter { it.status == "open" }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    val inProgressMaintenances: StateFlow<List<Maintenance>> = _maintenances.map { maintenances ->
        maintenances.filter { it.status == "in progress" }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    val doneMaintenances: StateFlow<List<Maintenance>> = _maintenances.map { maintenances ->
        maintenances.filter { it.status == "done" }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    val cancelledMaintenances: StateFlow<List<Maintenance>> = _maintenances.map { maintenances ->
        maintenances.filter { it.status == "cancelled" }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}