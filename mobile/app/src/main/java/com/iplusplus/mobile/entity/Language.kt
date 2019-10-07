package com.iplusplus.mobile.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Language (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val name:String
)