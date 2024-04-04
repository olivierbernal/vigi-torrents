package fr.oworld.vigiTorrent.ui.quickAlerting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mapbox.maps.extension.style.expressions.dsl.generated.all
import fr.oworld.vigiTorrent.AllAlertingViewModel
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.databinding.FragmentEndAlertingBinding
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel

class EndAlertingFragment: Fragment() {

    private var _binding: FragmentEndAlertingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var alertingViewModel: AlertingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        alertingViewModel = ViewModelProvider(requireActivity())[AlertingViewModel::class.java];
        _binding = FragmentEndAlertingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.allAlertingBtn.setOnClickListener {
            val allAlerting = ViewModelProvider(requireActivity())[AllAlertingViewModel::class.java]
            allAlerting.selectedAlerting = alertingViewModel.alerting.value!!

            findNavController().navigate(R.id.end_alerting_to_alertings_map)
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.end_alerting_to_home)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}