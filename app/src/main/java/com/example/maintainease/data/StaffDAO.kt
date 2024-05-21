package com.example.maintainease.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StaffDAO {
    @Insert
    suspend fun addStaffMember(staff: Staff)

    @Query("SELECT * FROM staff WHERE name = :name")
    suspend fun getStaffByName(name: String): Staff?

}