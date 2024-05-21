package com.example.maintainease.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface StaffDAO {
    @Insert
    suspend fun addStaffMember(staff: Staff)

}