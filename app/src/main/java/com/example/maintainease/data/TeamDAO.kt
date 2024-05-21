package com.example.maintainease.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TeamDAO {
    @Insert
    suspend fun addTeam(team: Team)

}