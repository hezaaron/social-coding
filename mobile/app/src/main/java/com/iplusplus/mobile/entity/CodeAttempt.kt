package com.iplusplus.mobile.entity

import androidx.room.*

@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"]),
                      ForeignKey(entity = Answer::class, parentColumns = ["id"], childColumns = ["answer_id"])
])
data class CodeAttempt (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = "user_id") val userId:Int,
    @Embedded(prefix = "code_") val code:Code,
    @ColumnInfo(name = "answer_id") val answerId:Int,
    @ColumnInfo(name = "user_answer") val userAnswer:String,
    @ColumnInfo(name = "is_correct") val correct:Boolean
)