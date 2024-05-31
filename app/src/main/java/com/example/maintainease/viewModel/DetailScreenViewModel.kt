package com.example.maintainease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maintainease.data.Maintenance
import com.example.maintainease.data.MaintenanceWithAssignee
import com.example.maintainease.data.Staff
import com.example.maintainease.data.getCurrentUser
import com.example.maintainease.repositories.MaintenanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val repository: MaintenanceRepository, private val taskId: Int) : ViewModel() {
    private val _maintenance = MutableStateFlow<MaintenanceWithAssignee?>(null)
    val maintenance: StateFlow<MaintenanceWithAssignee?> = _maintenance
    private val _assignee = MutableStateFlow<Staff?>(null)
    val assignee: StateFlow<Staff?> = _assignee

    init {
        viewModelScope.launch {
            repository.getMaintenanceWithAssigneeById(taskId).collectLatest { maintenance ->
                _maintenance.value = maintenance
            }
        }
        viewModelScope.launch {
            repository.getAssignee(taskId).collectLatest { assignee ->
                _assignee.value = assignee
            }
        }
    }

    suspend fun assignToMe() {
        getCurrentUser()["staffId"]?.let { repository.assignToMe(taskId, it) }
    }

    suspend fun addComment(comment: String) {
        val task = maintenance.value?.maintenance

        if (task != null) {
            val c = task.comments.toMutableList()
            c.add(0, comment)
            task.comments = c
        }
        maintenance.value?.let { repository.addCommentToTask(it.maintenance) }
    }

    suspend fun updateStatus(status: String){
        val task = maintenance.value?.maintenance
        task?.status = status
            maintenance.value?.let {repository.updateStatus(it.maintenance) }

    }

}
