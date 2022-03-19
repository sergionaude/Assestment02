package com.example.assestment02.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assestment02.model.songItem

@Database(entities = [(songItem::class)], version = 1)
abstract class SongRoomDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object{
        private var INSTANCE: SongRoomDatabase? = null

        fun getDatabase(context: Context?): SongRoomDatabase? {
            if(INSTANCE == null){
                synchronized(SongRoomDatabase::class.java){
                    if (INSTANCE == null){
                        context?.let {
                            INSTANCE =
                                Room.databaseBuilder<SongRoomDatabase>(it.applicationContext,
                                    SongRoomDatabase::class.java, "SongDB").build()
                        }
                    }
                }
            }
            return INSTANCE
        }
    }
}