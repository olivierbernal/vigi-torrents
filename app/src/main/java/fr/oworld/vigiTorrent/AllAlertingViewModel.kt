package fr.oworld.vigiTorrent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.oworld.vigiTorrent.data.Alerting

class AllAlertingViewModel : ViewModel() {

    private val _allAlerting = MutableLiveData<MutableMap<Int, Alerting>>().apply {
        value = mutableMapOf()
    }
    val alertingMap: LiveData<MutableMap<Int, Alerting>> = _allAlerting

    public var selectedAlerting: Alerting? = null
}