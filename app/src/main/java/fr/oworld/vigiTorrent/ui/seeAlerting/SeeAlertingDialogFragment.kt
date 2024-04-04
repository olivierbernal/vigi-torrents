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
import androidx.core.graphics.drawable.toDrawable
import androidx.core.text.toSpannable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.tools.FirebaseTools
import fr.oworld.vigiTorrent.databinding.FragmentSeeAlertingBinding
import fr.oworld.vigiTorrent.ui.startAlerting.SeeAlertingViewModel
import java.text.DateFormat


class SeeAlertingDialogFragment: DialogFragment() {

    private var _binding: FragmentSeeAlertingBinding? = null
    private lateinit var alertingViewModel: SeeAlertingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String?): SeeAlertingDialogFragment {
            val frag = SeeAlertingDialogFragment()
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

        _binding = FragmentSeeAlertingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        binding.imageTorrent.setImageBitmap(FirebaseTools.getBitmap(alertingViewModel.alerting.value!!.media[0]))
        binding.imageTorrent.rotation = 90.0f

        setSpan(getString(R.string.nomTorrent) + ": ",
            alertingViewModel.alerting.value!!.nameTorrent,
            binding.torrentName)
        binding.torrentColor.setImageBitmap(alertingViewModel.alerting.value!!.getColorIcon(requireContext()))

        binding.eventTypeTxt.setText(alertingViewModel.alerting.value!!.eventType)

        setSpan(getString(R.string.coordonn_es) + ": ",
            alertingViewModel.alerting.value!!.longitude.toString() +
                    "; " +
                    alertingViewModel.alerting.value!!.latitude.toString(),
            binding.pointTxt)

        setSpan(getString(R.string.date) + ": ",
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(alertingViewModel.alerting.value!!.date.toDate()),
            binding.hourTxt)

        setSpan(getString(R.string.commentaire),
            "",
            binding.commentTitleTxt)

        binding.commentTxt.text = alertingViewModel.alerting.value!!.comment.toString()

        if (alertingViewModel.alerting.value!!.waterHeight!!.isEmpty() &&
            alertingViewModel.alerting.value!!.overflowWaterHeight.isEmpty()) {
            binding.waterHeightLL.visibility = View.GONE
        }
        else if(alertingViewModel.alerting.value!!.waterHeight!!.isNotEmpty()) {
            setSpan(getString(R.string.hauteur_d_eau) + ": ",
                alertingViewModel.alerting.value!!.waterHeight!!,
                binding.waterHeightTxt)
        } else {
            setSpan(getString(R.string.hauteur_d_eau) + ": ",
                alertingViewModel.alerting.value!!.overflowWaterHeight.toString(),
                binding.waterHeightTxt)
        }

        if (alertingViewModel.alerting.value!!.speed!!.isEmpty()) {
            binding.speedLL.visibility = View.GONE
        }
        else {
            setSpan(getString(R.string.vitesse) + ": ",
                alertingViewModel.alerting.value!!.speed!!,
                binding.speedTxt)
        }

        if (alertingViewModel.alerting.value!!.speed!!.isEmpty()) {
            binding.floatingLL.visibility = View.GONE
        }
        else {
            setSpan(getString(R.string.transport_d_l_ments_flottants) + ": ",
                alertingViewModel.alerting.value!!.floatingElement!!,
                binding.floatingTxt)
        }

        if (alertingViewModel.alerting.value!!.clarity!!.isEmpty()) {
            binding.clarityLL.visibility = View.GONE
        }
        else {
            setSpan(getString(R.string.clart_de_l_eau) + ": ",
                alertingViewModel.alerting.value!!.clarity!!,
                binding.clarityTxt)
        }

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        return root
    }

    fun setSpan(title: String, value: String, textView: TextView) {
        val spannable = SpannableString(title + value)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            title.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            StyleSpan(Typeface.NORMAL),
            title.length,
            spannable.toString().length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.setText(spannable)
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