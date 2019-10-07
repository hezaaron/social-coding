package com.iplusplus.mobile.dao

import androidx.room.Dao
import androidx.room.Query
import com.iplusplus.mobile.entity.Code

@Dao
interface CodeDao {
    @Query("SELECT * FROM code WHERE language_id = :languageId")
    fun findByLanguageId(languageId:Int): List<Code>
}