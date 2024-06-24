package com.example.maintainease.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.maintainease.data.entities.Staff
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffDAO {
    @Insert
    suspend fun addStaffMember(staff: Staff)

    @Query("INSERT INTO teamStaffRelation (staffId, teamId) VALUES (:staffId, :teamId)")
    suspend fun mapStaffToTeam(staffId: Int, teamId: Int)

    @Query("Select * FROM Staff")
    fun getFullStaff(): Flow<List<Staff>?>

}