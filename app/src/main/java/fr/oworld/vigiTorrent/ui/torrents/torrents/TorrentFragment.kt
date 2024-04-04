package fr.oworld.vigiTorrent.ui.torrents

import android.R.attr.path
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.util.saveTo
import fr.oworld.vigiTorrent.AllAlertingViewModel
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.Alerting
import fr.oworld.vigiTorrent.databinding.FragmentTorrentBinding
import fr.oworld.vigiTorrent.ui.expertAlerting.SmsDialogFragment
import fr.oworld.vigiTorrent.ui.torrents.torrents.ImageAdapter
import fr.oworld.vigiTorrent.ui.torrents.torrents.ImageViewActivity


class TorrentFragment: Fragment() {
    private var _binding: FragmentTorrentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var torrentsViewModel: TorrentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTorrentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        torrentsViewModel = ViewModelProvider(requireActivity())[TorrentsViewModel::class.java]

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        val arrayList = ArrayList<Drawable>()

        //Add multiple images to arraylist.
        for( image in torrentsViewModel.torrent.value!!.images) {
            arrayList.add(AppCompatResources.getDrawable(requireContext(), image)!!)
        }
        val adapter = ImageAdapter(requireContext(), arrayList)
        binding.recycler.setAdapter(adapter)

        adapter.setOnItemClickListener(object : ImageAdapter.OnItemClickListener {
            override fun onClick(imageView: ImageView?, position: Int) {
                //Do something like opening the image in new activity or showing it in full screen or something else.
                startActivity(
                    Intent(
                        requireContext(),
                        ImageViewActivity::class.java
                    ).putExtra("image", torrentsViewModel.torrent.value!!.images[position]),
                    ActivityOptions.makeSceneTransitionAnimation(
                        requireActivity(),
                        imageView,
                        "image"
                    ).toBundle()
                )

            }
        })

        setSpan(getString(R.string.nomTorrent) + " : ",
            torrentsViewModel.torrent.value!!.nameTorrent,
            binding.torrentName)

        setSpan("Communes concernées" + " : ",
            torrentsViewModel.torrent.value!!.communes,
            binding.communesTxt)

        setSpan("Longueur" + " : ",
            torrentsViewModel.torrent.value!!.longeur,
            binding.longueurTxt)

        setSpan("Altitude max" + " : ",
            torrentsViewModel.torrent.value!!.alti_max,
            binding.altiMaxTxt)

        setSpan("Altitude min" + " : ",
            torrentsViewModel.torrent.value!!.alti_min,
            binding.altiMinTxt)

        setSpan("Affluent de" + " : ",
            torrentsViewModel.torrent.value!!.affluent,
            binding.affluentTxt)

        setSpan("Niveaux d'aléas" + " : ",
            torrentsViewModel.torrent.value!!.niveauAlea,
            binding.niveauAleasTxt)

        setSpan("Historique des événements" + " : ",
            torrentsViewModel.torrent.value!!.historique,
            binding.historiqueTxt)

        setSpan("Evénements" + " : ",
            torrentsViewModel.torrent.value!!.evenement,
            binding.evenementsTxt)

        binding.pdfLL.setOnClickListener {
            startActivity(PdfViewerActivity.launchPdfFromPath(
                context = requireContext(),
                path = torrentsViewModel.torrent.value!!.pdf,
                pdfTitle = "",
                saveTo = saveTo.ASK_EVERYTIME,
                fromAssets = true
            ))
        }

        if(torrentsViewModel.torrent.value!!.pdfBis.isEmpty()){
            binding.pdfBisLL.visibility = View.GONE
        } else {
            binding.pdfBisLL.visibility = View.VISIBLE
            binding.pdfBisLL.setOnClickListener {
                startActivity(PdfViewerActivity.launchPdfFromPath(
                    context = requireContext(),
                    path = torrentsViewModel.torrent.value!!.pdfBis,
                    pdfTitle = "",
                    saveTo = saveTo.ASK_EVERYTIME,
                    fromAssets = true
                ))
            }
        }

        binding.signalementLL.setOnClickListener {
            val a = Alerting()
            if(torrentsViewModel.torrent.value!!.communes.contains(getString(R.string.claix))) {
                a.longitude = 5.66667
                a.latitude = 45.116669
            } else if(torrentsViewModel.torrent.value!!.communes.contains(getString(R.string.seyssinet))) {
                a.longitude = 5.65
                a.latitude = 45.166672
            }else if(torrentsViewModel.torrent.value!!.communes.contains(getString(R.string.varces_alli_res))) {
                a.longitude = 5.6833
                a.latitude = 45.0833
            }

            val allAlerting = ViewModelProvider(requireActivity())[AllAlertingViewModel::class.java]
            allAlerting.selectedAlerting = a

            findNavController().navigate(R.id.fiche_torrent_to_alertings_map)
        }

        binding.smsLL.setOnClickListener {
            val fm: FragmentManager = requireActivity().getSupportFragmentManager()
            val alertDialog: SmsDialogFragment = SmsDialogFragment.newInstance("Some title", torrentsViewModel.torrent.value!!.nameTorrent)

            alertDialog.show(fm, "fragment_alert")
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

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

