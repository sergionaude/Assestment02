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
import com.example.assestment02.adapter.AdapterSong
import com.example.assestment02.adapter.onClickedSong
import com.example.assestment02.databinding.FragmentClasicBinding
import com.example.assestment02.model.songItem
import com.example.assestment02.model.songR
import com.example.assestment02.presenter.*
import com.example.assestment02.rest.SongService

class ClasicFragment : Fragment(), onClickedSong, ClasicViewContract {

    private val binding by lazy {
        FragmentClasicBinding.inflate((layoutInflater))
    }

    private val trackAdapter by lazy {
        AdapterSong(this)
    }

    private val clasicPresenter: ClasicPresenterContract by lazy {
        ClasicPresenter(requireContext(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        binding.itemClasicRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = trackAdapter
        }
        clasicPresenter.checkNetwork()

        binding.mySwipRefreshClasic.setOnRefreshListener {
            clasicPresenter.getSongs()
            binding.mySwipRefreshClasic.isRefreshing = false
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        clasicPresenter.getSongs()
    }

    override fun onDestroy() {
        super.onDestroy()
        clasicPresenter.destroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ClasicFragment().apply {
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
        binding.itemClasicRecycler.visibility = View.GONE
        //binding.progressBar.visibility = View.VISIBLE
    }

    override fun songsSuccess(songR: songR) {
        //binding.progressBar.visibility = View.GONE
        binding.itemClasicRecycler.visibility = View.VISIBLE
        trackAdapter.updateSongs(songR)
    }

    override fun songsError(throwable: Throwable) {
        binding.itemClasicRecycler.visibility = View.GONE
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