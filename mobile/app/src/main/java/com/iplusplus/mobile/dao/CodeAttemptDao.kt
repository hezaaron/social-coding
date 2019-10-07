package com.iplusplus.mobile.dao

import androidx.room.Dao
import androidx.room.Query
import com.iplusplus.mobile.entity.CodeAttempt

@Dao
interface CodeAttemptDao {
    @Query("SELECT code_language_id, code_question, code_snippet, answer_id, " +
            "user_answer, is_correct FROM CodeAttempt WHERE user_id = :userId ORDER BY id DESC LIMIT 5")
    fun findTop5ByUserId(userId:Int): List<CodeAttempt>
}