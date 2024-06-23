package com.example.maintainease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maintainease.data.entities.Staff
import com.example.maintainease.data.entities.Team
import com.example.maintainease.repositories.MaintenanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val repository: MaintenanceRepository) : ViewModel() {
    private val _staffList = MutableStateFlow<List<Staff>?>(emptyList())
    val staffList: StateFlow<List<Staff>?> = _staffList

    private val _teamList = MutableStateFlow<List<Team>?>(emptyList())
    val teamList: StateFlow<List<Team>?> = _teamList

    init {
        viewModelScope.launch {
            repository.getFullStaff().collectLatest { staff ->
                _staffList.value = staff
            }
        }
        viewModelScope.launch {
            repository.getAllTeams().collectLatest { team ->
                _teamList.value = team
            }
        }
    }

    fun setCurrentUser(staffId: Int, teamId: Int) {
        val currentUser = mapOf("staffId" to staffId, "teamId" to teamId)
        repository.setCurrentUser(currentUser)
    }
}