package fr.oworld.vigiTorrent.ui.torrents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mapbox.maps.extension.style.expressions.dsl.generated.switchCase
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.Torrent
import fr.oworld.vigiTorrent.databinding.FragmentTorrentsBinding
import oworld.co.splitexpenses.UI.ListEvent.TorrentsListRecyclerAdapter


class TorrentsFragment: Fragment() {
    private var _binding: FragmentTorrentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: TorrentsListRecyclerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var torrentsViewModel: TorrentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTorrentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        torrentsViewModel = ViewModelProvider(requireActivity())[TorrentsViewModel::class.java]

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.commune.text = torrentsViewModel.commune.value!!

        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.listTorrent.layoutManager = linearLayoutManager

        adapter = TorrentsListRecyclerAdapter(torrentsViewModel.torrents.value!!)
        binding.listTorrent.adapter = adapter

        adapter.delegate = this
        adapter.delegateOnClick = object : OnTorrentClickedListener {
            override fun onTorrentClicked(t: String) {
                val torrent = Torrent()
                torrent.nameTorrent = t
                when(t) {
                    "Ruisseau de Cossey" -> {
                        torrent.communes = "Claix"
                        torrent.longeur = "2 903m"
                        torrent.alti_max = "550m"
                        torrent.alti_min = "230m"
                        torrent.affluent = "Le Drac"
                        torrent.niveauAlea = "T4, T2"
                        torrent.historique = "-"
                        torrent.pdf = "Pont_Claix_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.cossey_seyssins,
                            R.drawable.cossey_pl)
                    }
                    "Rif-Talon" -> {
                        torrent.communes = "Claix"
                        torrent.longeur = "3 958m"
                        torrent.alti_max = "810m"
                        torrent.alti_min = "250m"
                        torrent.affluent = "Le Lavanchon"
                        torrent.niveauAlea = "T4"
                        torrent.historique = "1890 à 2010"
                        torrent.evenement = "1890 à 2010"
                        torrent.pdf = "Pont_Claix_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.rif_talon_claix,
                            R.drawable.rif_talon_pl,
                            R.drawable.rif_talon_1,
                            R.drawable.rif_talon_2,
                            R.drawable.rif_talon_3)
                    }
                    "Ruisseau de Malhivert" -> {
                        torrent.communes = "Claix"
                        torrent.longeur = "805m"
                        torrent.alti_max = "610m"
                        torrent.alti_min = "420m"
                        torrent.affluent = "Le Rif Talon"
                        torrent.niveauAlea = "T4"
                        torrent.historique = "-"
                        torrent.pdf = "Pont_Claix_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.malhivert_claix,
                            R.drawable.malhivert)
                    }
                    "Ruisseau du Bessat" -> {
                        torrent.communes = "Claix"
                        torrent.communes = "Claix"
                        torrent.longeur = "828m"
                        torrent.alti_max = "770m"
                        torrent.alti_min = "450m"
                        torrent.affluent = "Le Rif Talon"
                        torrent.niveauAlea = "T4"
                        torrent.historique = "-"
                        torrent.pdf = "Pont_Claix_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.bessat_claix,
                            R.drawable.bessat_pl)
                    }
                    "La Pissarde" -> {
                        torrent.communes = "Claix, Varces Allières"
                        torrent.longeur = "7 030m"
                        torrent.alti_max = "1 600m"
                        torrent.alti_min = "245m"
                        torrent.affluent = "La Rubine"
                        torrent.niveauAlea = "C4, T4"
                        torrent.historique = "-"
                        torrent.pdf = "Pont_Claix_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.pissarde_claix,
                            R.drawable.pissarde_pl)
                    }
                    "Ruisseau de Arcelles" -> {
                        torrent.communes = "Seyssinet"
                        torrent.longeur = "1 377m"
                        torrent.alti_max = "510m"
                        torrent.alti_min = "230m"
                        torrent.affluent = "Le Drac"
                        torrent.niveauAlea = "T3"
                        torrent.historique = "-"
                        torrent.pdf = "Seyssinet_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.arcelles_seyssinet,
                            R.drawable.arcelles_pl)
                    }
                    "Ruisseau du Bouteillard" -> {
                        torrent.communes = "Seyssinet"
                        torrent.longeur = "1 408m"
                        torrent.alti_max = "940m"
                        torrent.alti_min = "630m"
                        torrent.affluent = "Le Furon"
                        torrent.niveauAlea = "T3"
                        torrent.historique = "-"
                        torrent.pdf = "Seyssinet_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.bouteillard_seyssinet,
                            R.drawable.bouteillard_pl)
                    }
                    "Gresse" -> {
                        torrent.communes = "Varces Allières"
                        torrent.longeur = "35 238m"
                        torrent.alti_max = "1 660m"
                        torrent.alti_min = "245m"
                        torrent.affluent = "C2"
                        torrent.niveauAlea = "-"
                        torrent.historique = "-"
                        torrent.pdf = "Varces_Allieres_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.gresse_varces,
                            R.drawable.gresse_pl)
                    }
                    "Lavanchon" -> {
                        torrent.communes = "Varces Allières"
                        torrent.longeur = "13 737m"
                        torrent.alti_min = "240m"
                        torrent.alti_max = "1 450m"
                        torrent.affluent = "Le Drac"
                        torrent.niveauAlea = "I4, C3"
                        torrent.historique = "-"
                        torrent.pdf = "Varces_Allieres_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.lavanchon_varces_claix,
                            R.drawable.lavanchon_pl)
                    }
                    "Marjoera" -> {
                        torrent.communes = "Varces Allières"
                        torrent.longeur = "2 477m"
                        torrent.alti_min = "302m"
                        torrent.alti_max = "260m"
                        torrent.affluent = "Le Lavanchon"
                        torrent.niveauAlea = "C3"
                        torrent.historique = "-"
                        torrent.pdf = "Varces_Allieres_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.marjoera_varces)
                    }
                    "Suze" -> {
                        torrent.communes = "Varces Allières"
                        torrent.longeur = "6 569m"
                        torrent.alti_min = "302m"
                        torrent.alti_max = "240m"
                        torrent.affluent = "Le Lavanchon"
                        torrent.niveauAlea = "I2, C3"
                        torrent.historique = "-"
                        torrent.pdf = "Varces_Allieres_aleas.pdf"
                        torrent.images = arrayOf(R.drawable.suze_varces)
                    }
                    "Pissechin" -> {
                        torrent.communes = "Varces Allières"
                        torrent.longeur = "2 165m"
                        torrent.alti_max = "1 150m"
                        torrent.alti_min = "720m"
                        torrent.affluent = "La Pissarde"
                        torrent.niveauAlea = "C4"
                        torrent.historique = "-"
                        torrent.pdf = "Varces_Allieres_aleas.pdf"
                        torrent.pdfBis = "fiche_pissechin.pdf"
                        torrent.images = arrayOf(R.drawable.pissechin_claix,
                            R.drawable.pissechin_pl)
                    }
                }
                torrentsViewModel.torrent.value = torrent
                findNavController().navigate(R.id.list_torrent_to_fiche_torrent)
            }
        }

        return root
    }

    override fun onStart() {
        super.onStart()

        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnTorrentClickedListener {
        fun onTorrentClicked(t : String)
    }
}

