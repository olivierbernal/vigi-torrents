package fr.oworld.vigiTorrent.ui.startAlerting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.oworld.vigiTorrent.data.Alerting
import fr.oworld.vigiTorrent.data.AlertingHistory

class SeeAlertingViewModel : ViewModel() {

    private val _alerting = MutableLiveData<Alerting>().apply {
        value = Alerting()
    }
    val alerting: MutableLiveData<Alerting> = _alerting

    var historyAlerting: AlertingHistory? = null
}