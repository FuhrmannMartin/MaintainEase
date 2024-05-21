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
    private val _assigneeId = MutableStateFlow<Int?>(null)
    val assigneeId: StateFlow<Int?> = _assigneeId

    init {
        viewModelScope.launch {
            repository.getMaintenanceById(taskId).collectLatest { maintenance ->
                _maintenance.value = maintenance
            }
            repository.getAssigneeId(taskId).collectLatest { i ->
                _assigneeId.value = i
            }
        }
    }
}
