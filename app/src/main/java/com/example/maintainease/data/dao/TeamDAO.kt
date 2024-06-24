package com.example.maintainease.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.maintainease.data.entities.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDAO {
    @Insert
    suspend fun addTeam(team: Team)

    @Query("Select * FROM Team")
    fun getAllTeams(): Flow<List<Team>?>
}