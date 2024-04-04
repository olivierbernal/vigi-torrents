package fr.oworld.vigiTorrent.ui.expertAlerting

import android.content.res.Resources
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.tools.FirebaseTools
import fr.oworld.vigiTorrent.databinding.FragmentSeeAlertingHistoryBinding
import fr.oworld.vigiTorrent.ui.startAlerting.SeeAlertingViewModel
import java.text.DateFormat


class SeeAlertingHistoryDialogFragment: DialogFragment() {

    private var _binding: FragmentSeeAlertingHistoryBinding? = null
    private lateinit var alertingViewModel: SeeAlertingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String?): SeeAlertingHistoryDialogFragment {
            val frag = SeeAlertingHistoryDialogFragment()
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
        dialog?.window?.setLayout(percentWidth.toInt(), LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        alertingViewModel = ViewModelProvider(requireActivity())[SeeAlertingViewModel::class.java];

        _binding = FragmentSeeAlertingHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        binding.nature.text = alertingViewModel.historyAlerting!!.nature
        binding.causeTxt.text = alertingViewModel.historyAlerting!!.causes
        binding.listeTxt.text = alertingViewModel.historyAlerting!!.liste_c
        binding.hourTxt.text = alertingViewModel.historyAlerting!!.date

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        this.setWidthPercent(90)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}