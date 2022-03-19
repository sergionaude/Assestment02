package com.example.assestment02.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assestment02.model.songItem

@Dao
interface SongDao {
    @Query("SELECT * FROM song")
    fun getAllSongs(): List<songItem>

    @Insert
    fun addSongs(songItem: songItem)
}