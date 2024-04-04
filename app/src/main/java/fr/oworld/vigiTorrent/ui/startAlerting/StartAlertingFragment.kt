package fr.oworld.vigiTorrent.ui.startAlerting

import android.app.Activity
import android.content.Intent
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.Alerting
import fr.oworld.vigiTorrent.databinding.FragmentAlertPhotoBinding
import java.io.File
import java.io.InputStream
import java.util.Calendar


class StartAlertingFragment: Fragment() {

    private var _binding: FragmentAlertPhotoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var uri: Uri
    private lateinit var cameraStartForResult: ActivityResultLauncher<Uri>
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    private lateinit var alertingViewModel: AlertingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertPhotoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        alertingViewModel = ViewModelProvider(requireActivity())[AlertingViewModel::class.java];

        cameraStartForResult = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) {
            if (it) {
                val inputStream: InputStream = requireActivity().contentResolver.openInputStream(uri)!!
                var exif = ExifInterface(inputStream)

                val latLong = floatArrayOf(0.0f,0.0f)
                val hasLatLong = exif.getLatLong(latLong)
                if (hasLatLong) {
                    alertingViewModel.alerting.value!!.longitude = latLong[1].toDouble()
                    alertingViewModel.alerting.value!!.latitude = latLong[0].toDouble()
                }
                inputStream.close()

                val byteArray = Alerting.getByteArray(requireActivity(), uri)

                val path = alertingViewModel.alerting.value!!.getNewPhotoPath()
                alertingViewModel.alerting.value!!.mediaPath.add(path)
                alertingViewModel.alerting.value!!.media[alertingViewModel.alerting.value!!.mediaPath.indexOf(path)] = byteArray

                findNavController().navigate(R.id.start_alerting_to_quick_alerting)
            }
        }

        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val imageUri: Uri = result.data?.data as Uri

                val inputStream: InputStream = requireActivity().contentResolver.openInputStream(imageUri)!!
                var exif = ExifInterface(inputStream)

                val latLong = floatArrayOf(0.0f,0.0f)
                val hasLatLong = exif.getLatLong(latLong)
                if (hasLatLong) {
                    alertingViewModel.alerting.value!!.longitude = latLong[1].toDouble()
                    alertingViewModel.alerting.value!!.latitude = latLong[0].toDouble()
                }
                inputStream.close()

                val byteArray = Alerting.getByteArray(requireActivity(), imageUri)

                val path = alertingViewModel.alerting.value!!.getNewPhotoPath()
                alertingViewModel.alerting.value!!.mediaPath.add(path)
                alertingViewModel.alerting.value!!.media[alertingViewModel.alerting.value!!.mediaPath.indexOf(path)] = byteArray

                findNavController().navigate(R.id.start_alerting_to_quick_alerting)
            }
        }

        binding.cameraImg.setOnClickListener {
            takePicture()
        }
        binding.mediaBtn.setOnClickListener {
            pickFromGallery()
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return root
    }
    fun takePicture() {
        val directory = File(context?.filesDir, "camera_images")
        if(!directory.exists()){
            directory.mkdirs()
        }
        val file = File(directory,"${Calendar.getInstance().timeInMillis}.png")
        uri = FileProvider.getUriForFile(requireContext(),
            requireActivity().applicationContext.packageName + ".provider", file);

        cameraStartForResult.launch(uri)
    }

    private fun pickFromGallery() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        imagePickerLauncher.launch(galleryIntent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}