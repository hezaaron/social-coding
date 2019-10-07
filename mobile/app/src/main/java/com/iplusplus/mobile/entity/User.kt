package com.iplusplus.mobile.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id:Int,
    val name:String,
    val email:String
)