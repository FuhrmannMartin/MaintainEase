package com.example.maintainease.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.maintainease.NavigationHandling
import com.example.maintainease.data.entities.Maintenance
import com.example.maintainease.data.entities.MaintenanceWithAssignee
import com.example.maintainease.data.entities.Staff
import com.example.maintainease.repositories.MaintenanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailScreenViewModel(
    private val repository: MaintenanceRepository,
    private val taskId: Int
) : ViewModel() {
    private val _maintenance = MutableStateFlow<MaintenanceWithAssignee?>(null)
    val maintenance: StateFlow<MaintenanceWithAssignee?> = _maintenance
    private val _assignee = MutableStateFlow<Staff?>(null)
    val assignee: StateFlow<Staff?> = _assignee
    private val _currentUserName = MutableStateFlow<String?>(null)
    val currentUserName: StateFlow<String?> = _currentUserName

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
        viewModelScope.launch {
            repository.getCurrentUserName()?.collectLatest { currentUserName ->
                _currentUserName.value = currentUserName
            }
        }
    }

    suspend fun assignToMe() {
        repository.getCurrentUser()["staffId"]?.let { repository.assignToMe(taskId, it) }
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

    suspend fun updateStatus(status: String) {
        val task = maintenance.value?.maintenance
        task?.status = status
        maintenance.value?.let {
            repository.updateStatus(it.maintenance)
        }
    }

    fun deleteTheTask(maintenance: Maintenance, navController: NavController) {
        viewModelScope.launch {
            try {
                repository.deleteMaintenance(maintenance)
                navController.navigate(NavigationHandling.OverviewScreen.route) {
                    popUpTo(NavigationHandling.OverviewScreen.route) {
                        inclusive = true
                    }
                }
            } catch (e: Exception) {
                Log.e("DetailScreenViewModel", "Error deleting task", e)
            }
        }
    }

    fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        val currentTimestamp: String = sdf.format(Date())
        return currentTimestamp
    }
}
