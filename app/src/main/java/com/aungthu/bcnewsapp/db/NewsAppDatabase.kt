package com.aungthu.bcnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aungthu.bcnewsapp.db.dao.NewsDao
import com.aungthu.bcnewsapp.db.model.NewsModel
import com.aungthu.bcnewsapp.utils.Constants.DATABASE_NAME

@Database(entities = [NewsModel::class], version = 3, exportSchema = false)
abstract class NewsAppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {

        @Volatile
        private var INSTANCE: NewsAppDatabase? = null

        fun getDatabaseClient(context: Context): NewsAppDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, NewsAppDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }

    }

}