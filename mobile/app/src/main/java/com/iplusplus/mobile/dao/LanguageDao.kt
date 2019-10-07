package com.iplusplus.mobile.dao

import androidx.room.Dao
import androidx.room.Query
import com.iplusplus.mobile.entity.Language

@Dao
interface LanguageDao {
    @Query("SELECT * FROM language")
    fun getAll(): List<Language>
    @Query("SELECT * FROM language WHERE id = :languageId")
    fun findById(languageId:Int): Language
    @Query("SELECT * FROM language WHERE name = :languageName")
    fun findByName(languageName:String): Language
}