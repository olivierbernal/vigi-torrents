package fr.oworld.vigiTorrent.ui.expertAlerting

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import fr.oworld.vigiTorrent.databinding.FragmentExpertClarityBinding
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel

class ExpertClarityDialogFragment: DialogFragment() {

    private var _binding: FragmentExpertClarityBinding? = null
    private lateinit var alertingViewModel: AlertingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String?): ExpertClarityDialogFragment {
            val frag = ExpertClarityDialogFragment()
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

        _binding = FragmentExpertClarityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        binding.clarityRadio1.setOnClickListener {
            binding.clarity1.callOnClick()
        }
        binding.clarityRadio2.setOnClickListener {
            binding.clarity2.callOnClick()
        }
        binding.clarityRadio3.setOnClickListener {
            binding.clarity3.callOnClick()
        }
        binding.clarity1.setOnClickListener {
            binding.clarityRadio1.isChecked = true
            binding.clarityRadio2.isChecked = false
            binding.clarityRadio3.isChecked = false
            alertingViewModel.alerting.value!!.clarity = binding.clarityTxt1.text.toString()
            dialog?.dismiss()
        }
        binding.clarity2.setOnClickListener {
            binding.clarityRadio1.isChecked = false
            binding.clarityRadio2.isChecked = true
            binding.clarityRadio3.isChecked = false
            alertingViewModel.alerting.value!!.clarity = binding.clarityTxt2.text.toString()
            dialog?.dismiss()
        }
        binding.clarity3.setOnClickListener {
            binding.clarityRadio1.isChecked = false
            binding.clarityRadio2.isChecked = false
            binding.clarityRadio3.isChecked = true
            alertingViewModel.alerting.value!!.clarity = binding.clarityTxt3.text.toString()
            dialog?.dismiss()
        }

        binding.okBtn.setOnClickListener {
            dialog?.dismiss()
        }

        if(alertingViewModel.alerting.value?.clarity == binding.clarityTxt1.text.toString()) {
            binding.clarityRadio1.isChecked = true
            binding.clarityRadio2.isChecked = false
            binding.clarityRadio3.isChecked = false
        } else if(alertingViewModel.alerting.value?.clarity == binding.clarityTxt2.text.toString()) {
            binding.clarityRadio1.isChecked = false
            binding.clarityRadio2.isChecked = true
            binding.clarityRadio3.isChecked = false
        } else if(alertingViewModel.alerting.value?.clarity == binding.clarityTxt3.text.toString()) {
            binding.clarityRadio1.isChecked = false
            binding.clarityRadio2.isChecked = false
            binding.clarityRadio3.isChecked = true
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