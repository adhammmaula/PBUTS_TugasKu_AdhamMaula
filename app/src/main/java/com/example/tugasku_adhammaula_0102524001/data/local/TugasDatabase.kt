package com.example.tugasku_adhammaula_0102524001.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TugasEntity::class], version = 1, exportSchema = false)
abstract class TugasDatabase : RoomDatabase() {
    abstract fun tugasDao(): TugasDao

    companion object {
        @Volatile
        private var INSTANCE: TugasDatabase? = null

        fun getDatabase(context: Context): TugasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TugasDatabase::class.java,
                    "tugasku_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}