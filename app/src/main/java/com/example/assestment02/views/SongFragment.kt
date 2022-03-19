package com.example.assestment02.views

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assestment02.R
import com.example.assestment02.adapter.AdapterSong
import com.example.assestment02.adapter.onClickedSong
import com.example.assestment02.databinding.FragmentSongBinding
import com.example.assestment02.model.songItem
import com.example.assestment02.model.songR
import com.example.assestment02.presenter.SongsPresenterContract
import com.example.assestment02.presenter.SongsViewContract
import com.example.assestment02.presenter.songPresenter
import com.example.assestment02.rest.SongService
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongFragment : Fragment(), onClickedSong, SongsViewContract  {

    private val binding by lazy {
        FragmentSongBinding.inflate((layoutInflater))
    }

    private val trackAdapter by lazy {
        AdapterSong(this)
    }


    var mediaPlayer: MediaPlayer? = SongService.mediaPlayer.apply {
        SongService.mediaPlayer?.stop()
    }

//    private var mediaPlayer: MediaPlayer? = null

    private val SongPresenter: SongsPresenterContract by lazy {
        songPresenter(requireContext(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mediaPlayer?.stop()
        binding.itemSongRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = trackAdapter
        }
        SongPresenter.checkNetwork()

        binding.mySwipRefresh.setOnRefreshListener {
            SongPresenter.getSongs()
            binding.mySwipRefresh.isRefreshing = false
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.stop()
    }
    override fun onResume() {
        super.onResume()
        SongPresenter.getSongs()
    }

    override fun onDestroy() {
        super.onDestroy()
        SongPresenter.destroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(): SongFragment {
            return SongFragment().apply {
                arguments = Bundle().apply {
                    //putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
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
        binding.itemSongRecycler.visibility = View.GONE
        //binding.progressBar.visibility = View.VISIBLE
    }

    override fun songsSuccess(songR: songR) {
        //binding.progressBar.visibility = View.GONE
        binding.itemSongRecycler.visibility = View.VISIBLE
        trackAdapter.updateSongs(songR)
    }

    override fun songsError(throwable: Throwable) {
        binding.itemSongRecycler.visibility = View.GONE
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