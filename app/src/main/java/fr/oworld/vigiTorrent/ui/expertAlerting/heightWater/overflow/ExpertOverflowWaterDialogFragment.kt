package fr.oworld.vigiTorrent.ui.expertAlerting

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import fr.oworld.vigiTorrent.databinding.FragmentExpertOverflowWaterBinding
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel

class ExpertOverflowWaterDialogFragment: DialogFragment() {

    private var _binding: FragmentExpertOverflowWaterBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var alertingViewModel: AlertingViewModel

    companion object {
        fun newInstance(title: String?): ExpertOverflowWaterDialogFragment {
            val frag = ExpertOverflowWaterDialogFragment()
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

        _binding = FragmentExpertOverflowWaterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        binding.overFlowWaterRadio1.setOnClickListener {
            binding.overFlowWater1.callOnClick()
        }
        binding.overFlowWaterRadio2.setOnClickListener {
            binding.overFlowWater2.callOnClick()
        }
        binding.overFlowWaterRadio3.setOnClickListener {
            binding.overFlowWater3.callOnClick()
        }
        binding.overFlowWaterRadio4.setOnClickListener {
            binding.overFlowWater4.callOnClick()
        }
        binding.overFlowWater1.setOnClickListener {
            binding.overFlowWaterRadio1.isChecked = !binding.overFlowWaterRadio1.isChecked
            if (binding.overFlowWaterRadio1.isChecked){
                alertingViewModel.alerting.value!!.overflowWaterHeight.add(binding.overFlowWaterTxt1.text.toString())
                alertingViewModel.alerting.value!!.waterHeight = ""
            } else {
                alertingViewModel.alerting.value!!.overflowWaterHeight.remove(binding.overFlowWaterTxt1.text.toString())
                alertingViewModel.alerting.value!!.waterHeight = ""
            }
        }
        binding.overFlowWater2.setOnClickListener {
            binding.overFlowWaterRadio2.isChecked = !binding.overFlowWaterRadio2.isChecked
            if (binding.overFlowWaterRadio2.isChecked){
                alertingViewModel.alerting.value!!.overflowWaterHeight.add(binding.overFlowWaterTxt2.text.toString())
                alertingViewModel.alerting.value!!.waterHeight = ""
            } else {
                alertingViewModel.alerting.value!!.overflowWaterHeight.remove(binding.overFlowWaterTxt2.text.toString())
                alertingViewModel.alerting.value!!.waterHeight = ""
            }
        }
        binding.overFlowWater3.setOnClickListener {
            binding.overFlowWaterRadio3.isChecked = !binding.overFlowWaterRadio3.isChecked
            if (binding.overFlowWaterRadio3.isChecked){
                alertingViewModel.alerting.value!!.overflowWaterHeight.add(binding.overFlowWaterTxt3.text.toString())
                alertingViewModel.alerting.value!!.waterHeight = ""
            } else {
                alertingViewModel.alerting.value!!.overflowWaterHeight.remove(binding.overFlowWaterTxt3.text.toString())
                alertingViewModel.alerting.value!!.waterHeight = ""
            }
            alertingViewModel.alerting.value!!.waterHeight = ""
        }
        binding.overFlowWater4.setOnClickListener {
            binding.overFlowWaterRadio4.isChecked = !binding.overFlowWaterRadio4.isChecked
            if (binding.overFlowWaterRadio4.isChecked){
                alertingViewModel.alerting.value!!.overflowWaterHeight.add(binding.overFlowWaterTxt4.text.toString())
                alertingViewModel.alerting.value!!.waterHeight = ""
            } else {
                alertingViewModel.alerting.value!!.overflowWaterHeight.remove(binding.overFlowWaterTxt4.text.toString())
                alertingViewModel.alerting.value!!.waterHeight = ""
            }
            alertingViewModel.alerting.value!!.waterHeight = ""
        }

        binding.okBtn.setOnClickListener {
            dialog?.dismiss()
        }

        if(alertingViewModel.alerting.value?.overflowWaterHeight!!.contains(binding.overFlowWaterTxt1.text.toString())) {
            binding.overFlowWaterRadio1.isChecked = true
        }
        if(alertingViewModel.alerting.value?.overflowWaterHeight!!.contains(binding.overFlowWaterTxt2.text.toString())) {
            binding.overFlowWaterRadio2.isChecked = true
        }
        if(alertingViewModel.alerting.value?.overflowWaterHeight!!.contains(binding.overFlowWaterTxt3.text.toString())) {
            binding.overFlowWaterRadio3.isChecked = true
        }
        if(alertingViewModel.alerting.value?.overflowWaterHeight!!.contains(binding.overFlowWaterTxt4.text.toString())) {
            binding.overFlowWaterRadio4.isChecked = true
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