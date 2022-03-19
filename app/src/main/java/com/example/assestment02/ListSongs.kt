package com.example.assestment02

import android.media.MediaPlayer
import com.example.assestment02.model.songItem
import com.example.assestment02.model.songR

object ListSongs {
    var songR: MutableList<songItem> = mutableListOf()

    fun addSongs(newSong: songR) {
        songR.clear()
        songR.addAll(newSong.songItems)
    }
}
