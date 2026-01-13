package com.example.adoptacaterpillar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adoptacaterpillar.data.local.dao.CatImageDao
import com.example.adoptacaterpillar.data.local.entity.CachedCatImage

@Database(entities = [CachedCatImage::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catImageDao(): CatImageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cat_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}