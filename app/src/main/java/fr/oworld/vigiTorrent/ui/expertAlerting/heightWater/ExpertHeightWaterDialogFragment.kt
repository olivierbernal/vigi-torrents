package fr.oworld.vigiTorrent.ui.expertAlerting

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import fr.oworld.vigiTorrent.databinding.FragmentExpertWaterBinding
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel


class ExpertHeightWaterDialogFragment: DialogFragment() {

    private var _binding: FragmentExpertWaterBinding? = null

    private lateinit var alertingViewModel: AlertingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String?): ExpertHeightWaterDialogFragment {
            val frag = ExpertHeightWaterDialogFragment()
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
        _binding = FragmentExpertWaterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }
        binding.waterRadio1.setOnClickListener {
            binding.water1.callOnClick()
        }
        binding.waterRadio2.setOnClickListener {
            binding.water2.callOnClick()
        }
        binding.water1.setOnClickListener {
            binding.waterRadio1.isChecked = true
            binding.waterRadio2.isChecked = false
            binding.waterRadio3.isChecked = false
            binding.waterRadio3.visibility = View.INVISIBLE
            alertingViewModel.alerting.value!!.waterHeight = binding.waterTxt1.text.toString()
            alertingViewModel.alerting.value!!.overflowWaterHeight = mutableListOf()
            dialog?.dismiss()
        }
        binding.water2.setOnClickListener {
            binding.waterRadio1.isChecked = false
            binding.waterRadio2.isChecked = true
            binding.waterRadio3.isChecked = false
            binding.waterRadio3.visibility = View.INVISIBLE
            alertingViewModel.alerting.value!!.waterHeight = binding.waterTxt2.text.toString()
            alertingViewModel.alerting.value!!.overflowWaterHeight = mutableListOf()
            dialog?.dismiss()
        }
        binding.water3.setOnClickListener {
            binding.waterRadio1.isChecked = false
            binding.waterRadio2.isChecked = false
            binding.waterRadio3.isChecked = true
            binding.waterRadio3.visibility = View.VISIBLE

            val fm: FragmentManager = requireActivity().getSupportFragmentManager()
            val alertDialog: ExpertOverflowWaterDialogFragment = ExpertOverflowWaterDialogFragment.newInstance("Some title")

            alertDialog.show(fm, "ExpertOverflowWaterDialogFragment")
        }

        binding.okBtn.setOnClickListener {
            dialog?.dismiss()
        }

        if(alertingViewModel.alerting.value?.waterHeight == binding.waterTxt1.text.toString()) {
            binding.waterRadio1.isChecked = true
            binding.waterRadio2.isChecked = false
            binding.waterRadio3.isChecked = false
            binding.waterRadio3.visibility = View.INVISIBLE
        } else if(alertingViewModel.alerting.value?.waterHeight == binding.waterTxt2.text.toString()) {
            binding.waterRadio1.isChecked = false
            binding.waterRadio2.isChecked = true
            binding.waterRadio3.isChecked = false
            binding.waterRadio3.visibility = View.INVISIBLE
        } else if(alertingViewModel.alerting.value?.overflowWaterHeight!!.isNotEmpty()) {
            binding.waterRadio1.isChecked = false
            binding.waterRadio2.isChecked = false
            binding.waterRadio3.isChecked = true
            binding.waterRadio3.visibility = View.VISIBLE
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