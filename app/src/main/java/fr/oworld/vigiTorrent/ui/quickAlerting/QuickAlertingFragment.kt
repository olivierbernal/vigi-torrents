package fr.oworld.vigiTorrent.ui.quickAlerting

import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.oworld.vigiTorrent.AllAlertingViewModel
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.Alerting
import fr.oworld.vigiTorrent.databinding.FragmentQuickAlertingBinding
import fr.oworld.vigiTorrent.ui.expertAlerting.ExpertAlertingDialogFragment
import fr.oworld.vigiTorrent.ui.expertAlerting.ExpertOffTrackDialogFragment
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel
import org.joda.time.DateTime
import java.io.File
import java.util.Calendar
import java.util.Locale


class QuickAlertingFragment: Fragment() {

    private var _binding: FragmentQuickAlertingBinding? = null
    private lateinit var alertingViewModel: AlertingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var uri: Uri
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraStartForResult: ActivityResultLauncher<Uri>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuickAlertingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        alertingViewModel = ViewModelProvider(requireActivity())[AlertingViewModel::class.java]

        binding.expertFormBtn.setOnClickListener {
            val fm: FragmentManager = requireActivity().getSupportFragmentManager()
            val alertDialog: ExpertAlertingDialogFragment = ExpertAlertingDialogFragment.newInstance("Some title")

            alertDialog.show(fm, "fragment_alert")
        }

        binding.okBtn.setOnClickListener {
            if (alertingViewModel.alerting.value!!.latitude == 0.0 || alertingViewModel.alerting.value!!.longitude == 0.0) {
                Toast.makeText(context, R.string.erreur_localisation, Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            } else if (alertingViewModel.alerting.value!!.comment!!.isEmpty()) {
                Toast.makeText(context, R.string.erreur_commentaire, Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            } else if (alertingViewModel.alerting.value!!.eventType!!.isEmpty()) {
                Toast.makeText(context, R.string.erreur_evenement, Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            binding.loading.visibility = View.VISIBLE

            alertingViewModel.alerting.value!!.sendToServer(requireContext(),{
                val allAlertingViewModel = ViewModelProvider(requireActivity())[AllAlertingViewModel::class.java]
                allAlertingViewModel.alertingMap.value!![alertingViewModel.alerting.value!!.id] = alertingViewModel.alerting.value!!

                binding.loading.visibility = View.INVISIBLE
                findNavController().navigate(R.id.quick_alerting_to_end_alerting)
            }, {
                binding.loading.visibility = View.INVISIBLE
            })
        }
        binding.hourLL.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR, hour)
                cal.set(Calendar.MINUTE, minute)
            }
            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            alertingViewModel.alerting.value?.date = DateTime(cal.time)
        }

        binding.expertFormBtn.text = binding.expertFormBtn.text.toString().uppercase(Locale.ROOT)

        binding.torrentLL.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.jindique_torrent)

            // Set up the input
            val input = EditText(requireContext())
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("OK") { dialog, which ->
                alertingViewModel.alerting.value?.nameTorrent = input.text.toString()
            }

            val alertDialog = builder.show()
            alertDialog.getWindow()?.clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
            )
            alertDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        binding.commentLL.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.jajoute_commentaire)

            // Set up the input
            val input = EditText(requireContext())
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("OK") { dialog, which ->
                alertingViewModel.alerting.value?.comment = input.text.toString()
            }

            val alertDialog = builder.show()
            alertDialog.getWindow()?.clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
            )
            alertDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        binding.eventType1Radio.setOnClickListener {
            binding.eventType1.callOnClick()
        }
        binding.eventType2Radio.setOnClickListener {
            binding.eventType2.callOnClick()
        }
        binding.eventType1.setOnClickListener {
            binding.eventType1Radio.isChecked = true
            binding.eventType2Radio.isChecked = false
            alertingViewModel.alerting.value!!.eventType = binding.eventType1Txt.text.toString()
        }
        binding.eventType2.setOnClickListener {
            binding.eventType1Radio.isChecked = false
            binding.eventType2Radio.isChecked = true
            val fm: FragmentManager = requireActivity().getSupportFragmentManager()
            val alertDialog: ExpertOffTrackDialogFragment = ExpertOffTrackDialogFragment.newInstance("Some title")

            alertDialog.show(fm, "fragment_alert")
        }
        binding.geolocLL.setOnClickListener {
            findNavController().navigate(R.id.quick_alerting_to_map)
        }

        binding.mediaLL.setOnClickListener {
            showChooseASource()
        }

        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val imageUri: Uri = result.data?.data as Uri
                val byteArray = Alerting.getByteArray(requireActivity(), imageUri)

                val path = alertingViewModel.alerting.value!!.getNewPhotoPath()
                alertingViewModel.alerting.value!!.mediaPath.add(path)
                alertingViewModel.alerting.value!!.media[alertingViewModel.alerting.value!!.mediaPath.indexOf(path)] = byteArray
            }
        }

        cameraStartForResult = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) {
            if (it) {
                val imageUri: Uri = uri
                val byteArray = Alerting.getByteArray(requireActivity(), imageUri)

                val path = alertingViewModel.alerting.value!!.getNewPhotoPath()
                alertingViewModel.alerting.value!!.mediaPath.add(path)
                alertingViewModel.alerting.value!!.media[alertingViewModel.alerting.value!!.mediaPath.indexOf(path)] = byteArray
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return root
    }


    private fun showChooseASource() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())

        dialogBuilder.setTitle("")
        dialogBuilder.setMessage("Photo du torrent")
        dialogBuilder.setPositiveButton(R.string.From_library, DialogInterface.OnClickListener { _, _ ->
            pickFromGallery()
        })
        dialogBuilder.setNegativeButton(R.string.take_picture, DialogInterface.OnClickListener { _, _ ->
            takeAPicture()
        })
        dialogBuilder.setNeutralButton(R.string.cancel, DialogInterface.OnClickListener { dialog, _ ->
            dialog.cancel()
        })
        val b = dialogBuilder.create()
        b.show()
    }

    private fun pickFromGallery() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        imagePickerLauncher.launch(galleryIntent)
    }

    private fun takeAPicture() {
        val directory = File(context?.filesDir, "camera_images")
        if(!directory.exists()){
            directory.mkdirs()
        }
        val file = File(directory,"${Calendar.getInstance().timeInMillis}.png")
        uri = FileProvider.getUriForFile(requireContext(),
            requireActivity().applicationContext.packageName + ".provider", file);

        cameraStartForResult.launch(uri)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

