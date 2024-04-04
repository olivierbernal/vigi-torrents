package fr.oworld.vigiTorrent.ui.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.viewport
import fr.oworld.vigiTorrent.AllAlertingViewModel
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.Alerting
import fr.oworld.vigiTorrent.data.AlertingHistory
import fr.oworld.vigiTorrent.databinding.FragmentMapAlertingsBinding
import fr.oworld.vigiTorrent.ui.expertAlerting.SeeAlertingDialogFragment
import fr.oworld.vigiTorrent.ui.expertAlerting.SeeAlertingHistoryDialogFragment
import fr.oworld.vigiTorrent.ui.startAlerting.SeeAlertingViewModel


class AlertingMapFragment: Fragment(), PermissionsListener {
    private var _binding: FragmentMapAlertingsBinding? = null
    private val binding get() = _binding!!

    internal var permissionsManager: PermissionsManager? = null

    private lateinit var pointAnnotationManager: PointAnnotationManager
    private lateinit var allAlertingViewModel: AllAlertingViewModel

    var historyAnnotations: MutableList<PointAnnotation> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapAlertingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        allAlertingViewModel = ViewModelProvider(requireActivity())[AllAlertingViewModel::class.java]

        val annotationApi = binding.mapView.annotations
        pointAnnotationManager = annotationApi.createPointAnnotationManager()

        for ((_, alerting) in allAlertingViewModel.alertingMap.value!!) {
            createAnotation(alerting)
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        addHistoryAnnot()

        return root
    }

    fun addHistoryAnnot(){
        for (alertingHistory in AlertingHistory.allHistory()) {
            historyAnnotations.add(createAnotationHistory(alertingHistory))
        }
    }

    fun removeHistoryAnnot(){
        pointAnnotationManager.delete(historyAnnotations)
        historyAnnotations = mutableListOf()
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
                    .zoom(16.0)
                    .center(Point.fromLngLat(lng,lat))
                    .bearing(0.0)
                    .pitch(0.0)
                    .build()

                // set camera position
                binding.mapView.mapboxMap.setCamera(cameraPosition)
            } else {
                if (allAlertingViewModel.selectedAlerting != null){
                    val cameraPosition = CameraOptions.Builder()
                        .zoom(12.0)
                        .center(Point.fromLngLat(allAlertingViewModel.selectedAlerting!!.longitude,
                            allAlertingViewModel.selectedAlerting!!.latitude))
                        .bearing(0.0)
                        .pitch(0.0)
                        .build()

                    // set camera position
                    binding.mapView.mapboxMap.setCamera(cameraPosition)
                    allAlertingViewModel.selectedAlerting = null
                } else {
                    binding.localisationBtn.callOnClick()
                }
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
                        .zoom(11.0)
                        .build()),
                    transition = viewport.makeImmediateViewportTransition()
                )
            }
        }

        binding.historyBtn.setOnClickListener {
            if (binding.historyBtn.alpha == 1.0f) {
                removeHistoryAnnot()
                binding.historyBtn.alpha = 0.5f
            } else {
                addHistoryAnnot()
                binding.historyBtn.alpha = 1.0f
            }
        }

        pointAnnotationManager.addClickListener(OnPointAnnotationClickListener { pointAnnotation ->
            for((_, alerting) in allAlertingViewModel.alertingMap.value!!) {
                val point = Point.fromLngLat(alerting.longitude!!,
                    alerting.latitude!!)
                if (point == pointAnnotation.point) {
                    val seeAlertingViewModel = ViewModelProvider(requireActivity())[SeeAlertingViewModel::class.java]
                    seeAlertingViewModel.alerting.value = alerting

                    val fm: FragmentManager = requireActivity().getSupportFragmentManager()
                    val alertDialog: SeeAlertingDialogFragment = SeeAlertingDialogFragment.newInstance("Some title")

                    alertDialog.show(fm, "ExpertHeightWaterDialogFragment")
                    return@OnPointAnnotationClickListener true
                }
            }

            for(alertingHistory in AlertingHistory.allHistory()) {
                val point = Point.fromLngLat(alertingHistory.longitude,
                    alertingHistory.latitude)
                if (point == pointAnnotation.point) {
                    val seeAlertingViewModel = ViewModelProvider(requireActivity())[SeeAlertingViewModel::class.java]
                    seeAlertingViewModel.historyAlerting = alertingHistory

                    val fm: FragmentManager = requireActivity().getSupportFragmentManager()
                    val alertDialog: SeeAlertingHistoryDialogFragment = SeeAlertingHistoryDialogFragment.newInstance("Some title")

                    alertDialog.show(fm, "ExpertHeightWaterDialogFragment")
                    return@OnPointAnnotationClickListener true
                }
            }

            return@OnPointAnnotationClickListener true
        })
        binding.mapView.mapboxMap.getStyle { style -> enableLocationComponent(style) }
    }

    fun createAnotationHistory(alertingHistory: AlertingHistory): PointAnnotation {
        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.liquid_15271680)
        icon!!.setTint(requireContext().getColor(R.color.mainBlue))
        val bitmap = icon.toBitmap(55,55)

        // Set options for the resulting symbol layer.
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            // Define a geographic coordinate.
            .withPoint(Point.fromLngLat(
                alertingHistory.longitude,
                alertingHistory.latitude
            ))
            .withIconSize(1.0)
            .withIconImage(bitmap)

        // Add the resulting pointAnnotation to the map.
        return  pointAnnotationManager.create(pointAnnotationOptions)
    }


    fun createAnotation(alerting: Alerting) {
        val icon = alerting.getColorIcon(requireContext())

        // Set options for the resulting symbol layer.
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            // Define a geographic coordinate.
            .withPoint(Point.fromLngLat(
                alerting.longitude,
                alerting.latitude
            ))
            .withIconSize(1.0)
            .withIconImage(icon)

        // Add the resulting pointAnnotation to the map.
        pointAnnotationManager.create(pointAnnotationOptions)

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