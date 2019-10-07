package com.iplusplus.mobile.dao

import androidx.room.Dao
import androidx.room.Query
import com.iplusplus.mobile.entity.Answer

@Dao
interface AnswerDao {
    @Query("SELECT * FROM answer WHERE code_id = :codeId")
    fun findByCodeId(codeId:Int): List<Answer>
    @Query("SELECT * FROM answer WHERE is_correct = :isCorrect")
    fun findByCorrect(isCorrect:Boolean): Answer
}