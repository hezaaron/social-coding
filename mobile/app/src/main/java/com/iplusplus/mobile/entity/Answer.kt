package com.iplusplus.mobile.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Code::class,
        parentColumns = ["id"], childColumns = ["code_id"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
)
])
data class Answer (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = "code_id") val codeId:Int,
    val name:String,
    @ColumnInfo(name = "is_correct") val correct:Boolean
)