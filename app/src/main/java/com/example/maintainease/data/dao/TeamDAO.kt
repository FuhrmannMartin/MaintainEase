package com.example.maintainease.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.maintainease.data.entities.Team

@Dao
interface TeamDAO {
    @Insert
    suspend fun addTeam(team: Team)

}