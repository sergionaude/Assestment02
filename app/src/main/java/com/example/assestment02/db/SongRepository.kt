package com.example.assestment02.db

import android.app.Application
import android.content.Context
import com.example.assestment02.model.songItem

class SongRepository(application: Context?) {
    private var songDao: SongDao?= null

    init {
        val db: SongRoomDatabase? = SongRoomDatabase.getDatabase(application)
        songDao = db?.songDao()
    }
}