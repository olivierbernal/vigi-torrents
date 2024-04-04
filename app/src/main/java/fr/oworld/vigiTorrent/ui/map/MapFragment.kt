package fr.oworld.vigiTorrent.ui.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.viewport
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.databinding.FragmentMapBinding
import fr.oworld.vigiTorrent.ui.startAlerting.AlertingViewModel


class MapFragment : Fragment(), OnMapClickListener, PermissionsListener {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    internal var permissionsManager: PermissionsManager? = null

    private lateinit var pointAnnotationManager: PointAnnotationManager
    private lateinit var alertingViewModel: AlertingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        alertingViewModel = ViewModelProvider(requireActivity())[AlertingViewModel::class.java];

        val annotationApi = binding.mapView.annotations
        pointAnnotationManager = annotationApi.createPointAnnotationManager()

        binding.mapView.mapboxMap.addOnMapClickListener(this)

        binding.okBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    internal fun enableLocationComponent(loadedMapStyle: Style) { // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(requireContext())) { // Get an instance of the LocationComponent.
            val lm = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var gps_enabled = false
            var network_enabled = false

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (ex: Exception) {
            }

            if (!gps_enabled ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
            ) {
                // notify user
                val builder =
                    AlertDialog.Builder(ContextThemeWrapper(requireContext(), R.style.AlertDialogTheme))
                builder.setTitle(R.string.error_gps_disabled)
                builder.setMessage(R.string.error_gps_disabled_details)

                builder.setPositiveButton(R.string.active_gps) { _, _ ->
                    val callGPSSettingIntent =
                        Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    activity?.startActivityForResult(callGPSSettingIntent, 0)
                }

                builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()

                binding.localisationBtn.isEnabled = false
                binding.localisationBtn.visibility = View.INVISIBLE

                // locate user in Grenoble city center
                val lat = 45.190222
                val lng = 5.722077
                val cameraPosition = CameraOptions.Builder()
                    .zoom(15.0)
                    .center(Point.fromLngLat(lng,lat))
                    .bearing(0.0)
                    .pitch(0.0)
                    .build()

                // set camera position
                binding.mapView.mapboxMap.setCamera(cameraPosition)
            } else {
                binding.localisationBtn.callOnClick()
                binding.localisationBtn.isEnabled = true
                binding.localisationBtn.visibility = View.VISIBLE
            }
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager!!.requestLocationPermissions(requireActivity())
        }
    }

    override fun onStart() {
        super.onStart()

        binding.localisationBtn.setOnClickListener {
            with(binding.mapView) {
                location.locationPuck = createDefault2DPuck(withBearing = true)
                location.enabled = true
                location.puckBearing = PuckBearing.COURSE

                viewport.transitionTo(
                    targetState = viewport.makeFollowPuckViewportState(FollowPuckViewportStateOptions
                        .Builder()
                        .pitch(0.0)
                        .zoom(14.0)
                        .build()),
                    transition = viewport.makeImmediateViewportTransition()
                )
            }
        }


        if(alertingViewModel.alerting.value!!.longitude != 0.0) {
            val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_map)!!.toBitmap()

            // Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                // Define a geographic coordinate.
                .withPoint(Point.fromLngLat(alertingViewModel.alerting.value!!.longitude,
                    alertingViewModel.alerting.value!!.latitude))
                .withIconSize(1.0)
                .withIconImage(icon)

            // Add the resulting pointAnnotation to the map.
            pointAnnotationManager.create(pointAnnotationOptions)
        }

        binding.mapView.mapboxMap.getStyle { style -> enableLocationComponent(style) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            if (activity != null) {
                requireActivity().onBackPressed()
            }
            return true;
        };
        return super.onOptionsItemSelected(item)
    }

    override fun onMapClick(point: Point): Boolean {
        //remove all point.
        pointAnnotationManager.deleteAll()

        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_map)!!.toBitmap()

        // Set options for the resulting symbol layer.
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            // Define a geographic coordinate.
            .withPoint(point)
            .withIconSize(1.0)
            .withIconImage(icon)

        // Add the resulting pointAnnotation to the map.
        pointAnnotationManager.create(pointAnnotationOptions)

        alertingViewModel.alerting.value!!.longitude = point.longitude()
        alertingViewModel.alerting.value!!.latitude = point.latitude()

        return true
    }


    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        Toast.makeText(requireContext(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG)
            .show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
           binding.mapView.mapboxMap.getStyle { style -> enableLocationComponent(style) }
        } else {
            Toast.makeText(
                requireContext(),
                R.string.user_location_permission_not_granted,
                Toast.LENGTH_LONG
            ) .show()
        }
    }
}