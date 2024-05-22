package com.example.maintainease.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StaffDAO {
    @Insert
    suspend fun addStaffMember(staff: Staff)

    @Query("INSERT INTO teamStaffRelation (staffId, teamId) VALUES (:staffId, :teamId)")
    suspend fun mapStaffToTeam(staffId: Int, teamId: Int)

}