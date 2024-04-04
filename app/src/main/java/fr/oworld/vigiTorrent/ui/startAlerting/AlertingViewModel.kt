package fr.oworld.vigiTorrent.ui.startAlerting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.oworld.vigiTorrent.data.Alerting

class AlertingViewModel : ViewModel() {

    private val _alerting = MutableLiveData<Alerting>().apply {
        value = Alerting()
    }
    val alerting: LiveData<Alerting> = _alerting
}