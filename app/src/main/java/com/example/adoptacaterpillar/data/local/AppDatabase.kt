package com.example.adoptacaterpillar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adoptacaterpillar.data.local.dao.BreedDao
import com.example.adoptacaterpillar.data.local.entity.CachedBreed
import com.example.adoptacaterpillar.data.local.entity.CachedCatImage

@Database(entities = [CachedCatImage::class, CachedBreed::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao

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
