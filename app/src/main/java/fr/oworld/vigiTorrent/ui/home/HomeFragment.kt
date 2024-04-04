package fr.oworld.vigiTorrent.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.ApiClient
import fr.oworld.vigiTorrent.databinding.FragmentHomeBinding
import fr.oworld.vigiTorrent.ui.expertAlerting.ExpertAlertingDialogFragment
import fr.oworld.vigiTorrent.ui.expertAlerting.SmsDialogFragment
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.alertBtn.setOnClickListener {
            findNavController().navigate(R.id.home_to_start_alerting)
        }

        binding.mapBtn.setOnClickListener {
            findNavController().navigate(R.id.home_to_map_all_alerting)
        }

        binding.smsBtn.setOnClickListener {
            val fm: FragmentManager = requireActivity().getSupportFragmentManager()
            val alertDialog: SmsDialogFragment = SmsDialogFragment.newInstance("Some title","")

            alertDialog.show(fm, "fragment_alert")
        }

        binding.allTorrentBtn.setOnClickListener {
            findNavController().navigate(R.id.home_to_see_communes)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}