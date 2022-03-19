package com.example.assestment02.adapter

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assestment02.ListSongs
import com.example.assestment02.R
import com.example.assestment02.model.songItem
import com.example.assestment02.model.songR
import com.squareup.picasso.Picasso
import okhttp3.internal.notifyAll

class AdapterSong( private val onSongClicked: onClickedSong)
    : RecyclerView.Adapter<songViewHolder>() {

    fun updateSongs(newSong: songR) {
        ListSongs.songR.clear()
        ListSongs.songR.addAll(newSong.songItems)
        ListSongs.addSongs(newSong)
        //Log.d("DATO", ListSongs.songR.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): songViewHolder {
        val songView = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return songViewHolder(songView, onSongClicked )
    }

    override fun onBindViewHolder(holder: songViewHolder, position: Int) {
        val songItem = ListSongs.songR[position]
        holder.bind(songItem)
    }

    override fun getItemCount(): Int = ListSongs.songR.size
}

class songViewHolder(itemView: View, private val onClickedSong: onClickedSong ): RecyclerView.ViewHolder(itemView) {

    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val collectionName: TextView = itemView.findViewById(R.id.collectionName)
    private val artworkUrl60: TextView = itemView.findViewById(R.id.artworkUrl60)
    private val trackPrice: TextView = itemView.findViewById(R.id.trackPrice)
    private val songPhoto: ImageView = itemView.findViewById(R.id.song_photo)

    fun bind( songItem: songItem ) {
        artistName.text = songItem.trackName
        collectionName.text = songItem.primaryGenreName
        artworkUrl60.text = songItem.country
        trackPrice.text = songItem.trackPrice.toString()

        itemView.setOnClickListener {
            onClickedSong.songClicked(songItem)
        }

        Picasso.get()
            .load(songItem.artworkUrl60)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .resize(250, 250)
            .into(songPhoto)
    }
}