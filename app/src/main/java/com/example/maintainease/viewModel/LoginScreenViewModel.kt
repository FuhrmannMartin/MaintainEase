package com.example.maintainease.viewModel

import androidx.lifecycle.ViewModel
import com.example.maintainease.data.entities.Staff
import com.example.maintainease.data.entities.Team
import com.example.maintainease.data.entities.getStaff
import com.example.maintainease.data.entities.getTeam
import com.example.maintainease.repositories.MaintenanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginScreenViewModel(private val repository: MaintenanceRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<Map<String, Int>>(emptyMap())
    val currentUser: StateFlow<Map<String, Int>> get() = _currentUser

    private val _staffList = MutableStateFlow<List<Staff>>(emptyList())
    val staffList: StateFlow<List<Staff>> get() = _staffList

    private val _teamList = MutableStateFlow<List<Team>>(emptyList())
    val teamList: StateFlow<List<Team>> get() = _teamList

    init {
        // Initialize with dummy data or fetch from repository
        _staffList.value = getStaff()
        _teamList.value = getTeam()
    }

    fun setCurrentUser(staffId: Int, teamId: Int) {
        _currentUser.value = mapOf("staffId" to staffId, "teamId" to teamId)
    }
}