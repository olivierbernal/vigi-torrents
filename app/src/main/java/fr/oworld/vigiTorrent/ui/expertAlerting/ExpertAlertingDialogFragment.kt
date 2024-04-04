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
import fr.oworld.vigiTorrent.databinding.FragmentExpertAlertingBinding
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel

class ExpertAlertingDialogFragment: DialogFragment() {

    private var _binding: FragmentExpertAlertingBinding? = null
    private lateinit var alertingViewModel: AlertingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String?): ExpertAlertingDialogFragment {
            val frag = ExpertAlertingDialogFragment()
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
        val percentHeight = rect.height() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), percentHeight.toInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        alertingViewModel = ViewModelProvider(requireActivity())[AlertingViewModel::class.java];

        _binding = FragmentExpertAlertingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        binding.okBtn.setOnClickListener {
            dialog?.dismiss()
        }

        binding.waterHeightLL.setOnClickListener {
            val fm: FragmentManager = requireActivity().getSupportFragmentManager()
            val alertDialog: ExpertHeightWaterDialogFragment = ExpertHeightWaterDialogFragment.newInstance("Some title")

            alertDialog.show(fm, "ExpertHeightWaterDialogFragment")
        }

        binding.speedLL.setOnClickListener {
            val fm: FragmentManager = requireActivity().getSupportFragmentManager()
            val alertDialog: ExpertSpeedDialogFragment = ExpertSpeedDialogFragment.newInstance("Some title")

            alertDialog.show(fm, "ExpertSpeedDialogFragment")
        }

        binding.floatingElementLL.setOnClickListener {
            val fm: FragmentManager = requireActivity().getSupportFragmentManager()
            val alertDialog: ExpertTransportDialogFragment = ExpertTransportDialogFragment.newInstance("Some title")

            alertDialog.show(fm, "ExpertTransportDialogFragment")
        }

        binding.clarityLL.setOnClickListener {
            val fm: FragmentManager = requireActivity().getSupportFragmentManager()
            val alertDialog: ExpertClarityDialogFragment = ExpertClarityDialogFragment.newInstance("Some title")

            alertDialog.show(fm, "ExpertClarityDialogFragment")
        }

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }
        binding.okBtn.setOnClickListener {
            dialog?.dismiss()
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