package com.example.assestment02.presenter

import android.content.Context
import com.example.assestment02.model.songR
import com.example.assestment02.rest.NetworkUtils
import com.example.assestment02.rest.SongService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class songPresenter(
    private var context: Context? = null,
    private var viewContract: SongsViewContract? = null,
    private val networkUtils: NetworkUtils = NetworkUtils(context),
    private val disposable: CompositeDisposable = CompositeDisposable(),
    //private val db: SongRepository = SongRepository(context)
) : SongsPresenterContract {

    override fun checkNetwork() {
        networkUtils.registerForNetworkState()
    }

    override fun getSongs() {
        viewContract?.loadingSongs(true)

        networkUtils.networkState
            .subscribe(
                { netState -> if (netState) {
                    doNetworkCall()
                } else {
                    viewContract?.songsError(Throwable("ERROR NO INTERNET CONNECTION"))
                } },
                { viewContract?.songsError(it) }
            ).apply {
                disposable.add(this)
            }
    }

    override fun destroy() {
        networkUtils.unregisterFromNetworkState()
        context = null
        viewContract = null
        disposable.dispose()
    }

    private fun doNetworkCall() {
        SongService.retrofitService.getPop()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> viewContract?.songsSuccess(response) },
                { error -> viewContract?.songsError(error) }
            ).apply {
                disposable.add(this)
            }
    }
}

interface SongsViewContract {
    fun loadingSongs(isLoading: Boolean)
    fun songsSuccess(songR: songR)
    fun songsError(throwable: Throwable)
}

interface SongsPresenterContract {
    fun getSongs()
    fun destroy()
    fun checkNetwork()
}