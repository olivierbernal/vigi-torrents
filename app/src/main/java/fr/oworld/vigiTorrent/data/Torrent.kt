package fr.oworld.vigiTorrent.data

import android.graphics.drawable.Drawable

class Torrent {
    var id: Int = 0
    var nameTorrent: String = ""
    var communes: String = ""

    var longeur: String = ""
    var alti_max: String = ""
    var alti_min: String = ""
    var images: Array<Int> = arrayOf()
    var affluent: String = ""

    var niveauAlea: String= ""

    var historique = ""
    var evenement = ""
    var pdf = ""
    var pdfBis = ""

    constructor(){

    }
}