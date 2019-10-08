package com.iplusplus.mobile.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Language::class,
        parentColumns = ["id"], childColumns = ["language_id"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
data class Code (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = "language_id", index = true)val languageId:Int,
    val question:String,
    val snippet:String
)