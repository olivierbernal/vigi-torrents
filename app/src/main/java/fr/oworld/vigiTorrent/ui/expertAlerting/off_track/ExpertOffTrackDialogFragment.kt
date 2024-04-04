package fr.oworld.vigiTorrent.ui.expertAlerting

import android.R
import android.app.AlertDialog
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import fr.oworld.vigiTorrent.databinding.FragmentExpertOffTrackBinding
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel

class ExpertOffTrackDialogFragment: DialogFragment() {

    private var _binding: FragmentExpertOffTrackBinding? = null
    private lateinit var alertingViewModel: AlertingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String?): ExpertOffTrackDialogFragment {
            val frag = ExpertOffTrackDialogFragment()
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

        _binding = FragmentExpertOffTrackBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.exitBtn.setOnClickListener {
            dialog?.dismiss()
        }

        binding.offTrackRadio1.setOnClickListener {
            binding.offTrack1.callOnClick()
        }
        binding.offTrackRadio2.setOnClickListener {
            binding.offTrack2.callOnClick()
        }
        binding.offTrackRadio3.setOnClickListener {
            binding.offTrack3.callOnClick()
        }
        binding.offTrackRadio4.setOnClickListener {
            binding.offTrack4.callOnClick()
        }
        binding.offTrack1.setOnClickListener {
            binding.offTrackRadio1.isChecked = true
            binding.offTrackRadio2.isChecked = false
            binding.offTrackRadio3.isChecked = false
            binding.offTrackRadio4.isChecked = false
            alertingViewModel.alerting.value!!.eventType = binding.offTrackTxt1.text.toString()
            dialog?.dismiss()
        }
        binding.offTrack2.setOnClickListener {
            binding.offTrackRadio1.isChecked = false
            binding.offTrackRadio2.isChecked = true
            binding.offTrackRadio3.isChecked = false
            binding.offTrackRadio4.isChecked = false
            alertingViewModel.alerting.value!!.eventType = binding.offTrackTxt2.text.toString()
            dialog?.dismiss()
        }
        binding.offTrack3.setOnClickListener {
            binding.offTrackRadio1.isChecked = false
            binding.offTrackRadio2.isChecked = false
            binding.offTrackRadio3.isChecked = true
            binding.offTrackRadio4.isChecked = false
            alertingViewModel.alerting.value!!.eventType = binding.offTrackTxt3.text.toString()
            dialog?.dismiss()
        }
        binding.offTrack4.setOnClickListener {
            binding.offTrackRadio1.isChecked = false
            binding.offTrackRadio2.isChecked = false
            binding.offTrackRadio3.isChecked = false
            binding.offTrackRadio4.isChecked = true
            alertingViewModel.alerting.value!!.eventType = binding.offTrackTxt4.text.toString()
            dialog?.dismiss()
        }
        binding.offTrack5.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle(fr.oworld.vigiTorrent.R.string.autres)

            // Set up the input
            val input = EditText(requireContext())
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.inputType = InputType.TYPE_CLASS_TEXT
            if(alertingViewModel.alerting.value?.clarity != binding.offTrackTxt1.text.toString() &&
                alertingViewModel.alerting.value?.clarity != binding.offTrackTxt2.text.toString() &&
                alertingViewModel.alerting.value?.clarity != binding.offTrackTxt3.text.toString() &&
                alertingViewModel.alerting.value?.clarity != binding.offTrackTxt4.text.toString() &&
                alertingViewModel.alerting.value?.clarity!!.isNotEmpty()) {
                input.setText(alertingViewModel.alerting.value?.clarity!!)
            }

            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("OK") { dialog, which ->
                alertingViewModel.alerting.value!!.eventType = input.text.toString()
            }

            builder.show()
        }

        binding.okBtn.setOnClickListener {
            dialog?.dismiss()
        }

        if(alertingViewModel.alerting.value?.eventType == binding.offTrackTxt1.text.toString()) {
            binding.offTrackRadio1.isChecked = true
            binding.offTrackRadio2.isChecked = false
            binding.offTrackRadio3.isChecked = false
            binding.offTrackRadio4.isChecked = false
            binding.offTrackRadio5.isChecked = false
            binding.offTrackRadio5.visibility = View.INVISIBLE
        } else if(alertingViewModel.alerting.value?.eventType == binding.offTrackTxt2.text.toString()) {
            binding.offTrackRadio1.isChecked = false
            binding.offTrackRadio2.isChecked = true
            binding.offTrackRadio3.isChecked = false
            binding.offTrackRadio4.isChecked = false
            binding.offTrackRadio5.isChecked = false
            binding.offTrackRadio5.visibility = View.INVISIBLE
        } else if(alertingViewModel.alerting.value?.eventType == binding.offTrackTxt3.text.toString()) {
            binding.offTrackRadio1.isChecked = false
            binding.offTrackRadio2.isChecked = false
            binding.offTrackRadio3.isChecked = true
            binding.offTrackRadio4.isChecked = false
            binding.offTrackRadio5.isChecked = false
            binding.offTrackRadio5.visibility = View.INVISIBLE
        } else if(alertingViewModel.alerting.value?.eventType == binding.offTrackTxt4.text.toString()) {
            binding.offTrackRadio1.isChecked = false
            binding.offTrackRadio2.isChecked = false
            binding.offTrackRadio3.isChecked = false
            binding.offTrackRadio4.isChecked = true
            binding.offTrackRadio5.isChecked = false
            binding.offTrackRadio5.visibility = View.INVISIBLE
        } else if(alertingViewModel.alerting.value?.eventType!!.isNotEmpty()) {
            binding.offTrackRadio1.isChecked = false
            binding.offTrackRadio2.isChecked = false
            binding.offTrackRadio3.isChecked = false
            binding.offTrackRadio4.isChecked = false
            binding.offTrackRadio5.isChecked = true
            binding.offTrackRadio5.visibility = View.VISIBLE
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