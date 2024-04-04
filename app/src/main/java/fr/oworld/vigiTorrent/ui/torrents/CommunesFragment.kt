package fr.oworld.vigiTorrent.ui.torrents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.util.saveTo
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.databinding.FragmentCommunesBinding


class CommunesFragment: Fragment() {
    private var _binding: FragmentCommunesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.claixLL.setOnClickListener {
            var model = ViewModelProvider(requireActivity())[TorrentsViewModel::class.java]
            model.torrents.value?.removeAll(model.torrents.value!!)
            model.torrents.value?.addAll(mutableListOf(
                "Ruisseau de Cossey",
                "Rif-Talon",
                "Ruisseau de Malhivert",
                "Ruisseau du Bessat",
                "La Pissarde"))
            model.commune.value = getString(R.string.claix)

            findNavController().navigate(R.id.list_communes_to_list_torrent)
        }
        binding.echirollesLL.setOnClickListener {
//            lien vers PDF
            startActivity(
                PdfViewerActivity.launchPdfFromPath(
                context = requireContext(),
                path = "Echirolles_aleas.pdf",
                pdfTitle = "",
                saveTo = saveTo.ASK_EVERYTIME,
                fromAssets = true
            ))
        }
        binding.pontClaixLL.setOnClickListener {
//            lien vers PDF
            startActivity(
                PdfViewerActivity.launchPdfFromPath(
                    context = requireContext(),
                    path = "Pont_Claix_aleas.pdf",
                    pdfTitle = "",
                    saveTo = saveTo.ASK_EVERYTIME,
                    fromAssets = true
                ))
        }
        binding.seyssinetLL.setOnClickListener {
            var model = ViewModelProvider(requireActivity())[TorrentsViewModel::class.java]
            model.torrents.value?.removeAll(model.torrents.value!!)
            model.torrents.value?.addAll(mutableListOf(
                "Ruisseau de Arcelles",
                "Ruisseau du Bouteillard"))
            model.commune.value = getString(R.string.seyssinet)

            findNavController().navigate(R.id.list_communes_to_list_torrent)
        }
        binding.varcesLL.setOnClickListener {
            var model = ViewModelProvider(requireActivity())[TorrentsViewModel::class.java]
            model.torrents.value?.removeAll(model.torrents.value!!)
            model.torrents.value?.addAll(mutableListOf(
                "Gresse",
                "Lavanchon",
                "Marjoera",
                "Suze",
                "La Pissarde",
                "Pissechin"))
            model.commune.value = getString(R.string.varces_alli_res)

            findNavController().navigate(R.id.list_communes_to_list_torrent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

