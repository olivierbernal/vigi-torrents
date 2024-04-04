package fr.oworld.vigiTorrent.ui.torrents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.oworld.vigiTorrent.data.Torrent

class TorrentsViewModel : ViewModel() {
    private val _torrents = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }
    val torrents: LiveData<MutableList<String>> = _torrents

    private val _commune = MutableLiveData<String>().apply {
        value = ""
    }
    val commune: MutableLiveData<String> = _commune

    private val _torrent = MutableLiveData<Torrent>().apply {
        value = Torrent()
    }
    val torrent: MutableLiveData<Torrent> = _torrent
}