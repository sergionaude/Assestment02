package com.example.assestment02.views

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assestment02.R
import com.example.assestment02.adapter.AdapterSong
import com.example.assestment02.adapter.onClickedSong
import com.example.assestment02.databinding.FragmentRockBinding
import com.example.assestment02.databinding.FragmentSongBinding
import com.example.assestment02.model.songItem
import com.example.assestment02.model.songR
import com.example.assestment02.presenter.*
import com.example.assestment02.rest.SongService
import com.example.assestment02.rest.SongService.mediaPlayer

class RockFragment : Fragment(), onClickedSong, RockViewContract {

    private val binding by lazy {
        FragmentRockBinding.inflate((layoutInflater))
    }

    private val trackAdapter by lazy {
        AdapterSong(this)
    }

    private val rockPresenter: RockPresenterContract by lazy {
        RockPresenter(requireContext(), this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding.itemRockRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = trackAdapter
        }
        rockPresenter.checkNetwork()

        binding.mySwipRefreshRock.setOnRefreshListener {
            rockPresenter.getSongs()
            binding.mySwipRefreshRock.isRefreshing = false
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        rockPresenter.getSongs()
    }

    override fun onDestroy() {
        super.onDestroy()
        rockPresenter.destroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RockFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun songClicked(songItem: songItem) {
        if(SongService.mediaPlayer!= null){
            SongService.mediaPlayer!!.stop()
            SongService.mediaPlayer!!.reset()
            SongService.mediaPlayer = MediaPlayer.create(this.context, songItem.previewUrl.toUri())
            SongService.mediaPlayer!!.start()
        }
        else{
            SongService.mediaPlayer = MediaPlayer.create(this.context, songItem.previewUrl.toUri())
            SongService.mediaPlayer!!.start()
        }
    }

    override fun loadingSongs(isLoading: Boolean) {
        binding.itemRockRecycler.visibility = View.GONE
        //binding.progressBar.visibility = View.VISIBLE
    }

    override fun songsSuccess(songR: songR) {
        //binding.progressBar.visibility = View.GONE
        binding.itemRockRecycler.visibility = View.VISIBLE
        trackAdapter.updateSongs(songR)
    }

    override fun songsError(throwable: Throwable) {
        binding.itemRockRecycler.visibility = View.GONE
        //binding.progressBar.visibility = View.GONE

        AlertDialog.Builder(requireContext())
            .setTitle("AN ERROR HAS OCCURRED")
            .setMessage(throwable.localizedMessage)
            .setPositiveButton("DISMISS") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }
}