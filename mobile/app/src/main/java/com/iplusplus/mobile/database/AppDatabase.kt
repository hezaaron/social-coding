package com.iplusplus.mobile.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iplusplus.mobile.dao.*
import com.iplusplus.mobile.entity.*

@Database(entities = [Language::class, Code::class, Answer::class,
                      CodeAttempt::class, User::class], version=2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun languageDao() : LanguageDao
    abstract fun codeDao() : CodeDao
    abstract fun answerDao() : AnswerDao
    abstract fun codeAttemptDao() : CodeAttemptDao
    abstract fun userDao() : UserDao
}