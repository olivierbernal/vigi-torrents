package fr.oworld.vigiTorrent.ui.expertAlerting

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import fr.oworld.vigiTorrent.databinding.FragmentExpertSpeedBinding
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel

class ExpertSpeedDialogFragment: DialogFragment() {

    private var _binding: FragmentExpertSpeedBinding? = null
    private lateinit var alertingViewModel: AlertingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String?): ExpertSpeedDialogFragment {
            val frag = ExpertSpeedDialogFragment()
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
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        alertingViewModel = ViewModelProvider(requireActivity())[AlertingViewModel::class.java];

        _binding = FragmentExpertSpeedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }
        
        binding.speedRadio1.setOnClickListener {
            binding.speed1.callOnClick()
        }
        binding.speedRadio2.setOnClickListener {
            binding.speed2.callOnClick()
        }

        binding.speed1.setOnClickListener {
            binding.speedRadio1.isChecked = true
            binding.speedRadio2.isChecked = false
            alertingViewModel.alerting.value!!.speed = binding.speedTxt1.text.toString()
            dialog?.dismiss()
        }
        binding.speed2.setOnClickListener {
            binding.speedRadio1.isChecked = false
            binding.speedRadio2.isChecked = true
            alertingViewModel.alerting.value!!.speed = binding.speedTxt2.text.toString()
            dialog?.dismiss()
        }

        binding.okBtn.setOnClickListener {
            dialog?.dismiss()
        }

        if(alertingViewModel.alerting.value?.speed == binding.speedTxt1.text.toString()) {
            binding.speedRadio1.isChecked = true
            binding.speedRadio2.isChecked = false
        } else if(alertingViewModel.alerting.value?.speed == binding.speedTxt2.text.toString()) {
            binding.speedRadio1.isChecked = false
            binding.speedRadio2.isChecked = true
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