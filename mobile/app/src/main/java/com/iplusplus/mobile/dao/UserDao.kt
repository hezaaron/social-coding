package com.iplusplus.mobile.dao

import androidx.room.Dao
import androidx.room.Query
import com.iplusplus.mobile.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>
    @Query("SELECT * FROM user WHERE id = :userId")
    fun findByUserId(userId:Int): User
}