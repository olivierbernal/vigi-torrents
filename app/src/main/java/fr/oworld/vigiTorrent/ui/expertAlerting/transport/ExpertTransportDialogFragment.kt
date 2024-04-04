package fr.oworld.vigiTorrent.ui.expertAlerting

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import fr.oworld.vigiTorrent.databinding.FragmentExpertTransportBinding
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel


class ExpertTransportDialogFragment: DialogFragment() {

    private var _binding: FragmentExpertTransportBinding? = null
    private lateinit var alertingViewModel: AlertingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String?): ExpertTransportDialogFragment {
            val frag = ExpertTransportDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.setArguments(args)
            return frag
        }
    }

    fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        alertingViewModel = ViewModelProvider(requireActivity())[AlertingViewModel::class.java];

        _binding = FragmentExpertTransportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        binding.transportRadio1.setOnClickListener {
            binding.transport1.callOnClick()
        }
        binding.transportRadio2.setOnClickListener {
            binding.transport2.callOnClick()
        }
        binding.transportRadio3.setOnClickListener {
            binding.transport3.callOnClick()
        }
        binding.transportRadio4.setOnClickListener {
            binding.transport4.callOnClick()
        }
        binding.transport1.setOnClickListener {
            binding.transportRadio1.isChecked = true
            binding.transportRadio2.isChecked = false
            binding.transportRadio3.isChecked = false
            binding.transportRadio4.isChecked = false
            alertingViewModel.alerting.value!!.floatingElement = binding.transportTxt1.text.toString()
            dialog?.dismiss()
        }
        binding.transport2.setOnClickListener {
            binding.transportRadio1.isChecked = false
            binding.transportRadio2.isChecked = true
            binding.transportRadio3.isChecked = false
            binding.transportRadio4.isChecked = false
            alertingViewModel.alerting.value!!.floatingElement = binding.transportTxt2.text.toString()
            dialog?.dismiss()
        }
        binding.transport3.setOnClickListener {
            binding.transportRadio1.isChecked = false
            binding.transportRadio2.isChecked = false
            binding.transportRadio3.isChecked = true
            binding.transportRadio4.isChecked = false
            alertingViewModel.alerting.value!!.floatingElement = binding.transportTxt3.text.toString()
            dialog?.dismiss()
        }
        binding.transport4.setOnClickListener {
            binding.transportRadio1.isChecked = false
            binding.transportRadio2.isChecked = false
            binding.transportRadio3.isChecked = false
            binding.transportRadio4.isChecked = true
            alertingViewModel.alerting.value!!.floatingElement = binding.transportTxt4.text.toString()
            dialog?.dismiss()
        }

        binding.okBtn.setOnClickListener {
            dialog?.dismiss()
        }

        if(alertingViewModel.alerting.value?.floatingElement == binding.transportTxt1.text.toString()) {
            binding.transportRadio1.isChecked = true
            binding.transportRadio2.isChecked = false
            binding.transportRadio3.isChecked = false
            binding.transportRadio4.isChecked = false
        } else if(alertingViewModel.alerting.value?.floatingElement == binding.transportTxt2.text.toString()) {
            binding.transportRadio1.isChecked = false
            binding.transportRadio2.isChecked = true
            binding.transportRadio3.isChecked = false
            binding.transportRadio4.isChecked = false
        } else if(alertingViewModel.alerting.value?.floatingElement == binding.transportTxt3.text.toString()) {
            binding.transportRadio1.isChecked = false
            binding.transportRadio2.isChecked = false
            binding.transportRadio3.isChecked = true
            binding.transportRadio4.isChecked = false
        } else if(alertingViewModel.alerting.value?.floatingElement == binding.transportTxt4.text.toString()) {
            binding.transportRadio1.isChecked = false
            binding.transportRadio2.isChecked = false
            binding.transportRadio3.isChecked = false
            binding.transportRadio4.isChecked = true
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        this.setWidthPercent(85)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}