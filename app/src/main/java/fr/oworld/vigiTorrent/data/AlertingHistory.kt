package fr.oworld.vigiTorrent.data

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import fr.oworld.vigiTorrent.R
import fr.oworld.vigiTorrent.data.tools.FirebaseTools
import okhttp3.ResponseBody
import org.joda.time.DateTime
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


class AlertingHistory {
    var id: String = ""
    var nature: String = ""
    var causes: String = ""

    var liste_c: String = ""
    var date: String = ""

    var longitude: Double = 0.0
    var latitude: Double = 0.0

    var dateFormatter: SimpleDateFormat
    init {
        this.dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        this.dateFormatter.timeZone = TimeZone.getTimeZone("UTC")
    }

    constructor(){

    }

    companion object {
        fun allHistory(): Array<AlertingHistory> {
            return arrayOf(history1(),history2(),history3(),history4(),history5(),
                history6(),history7(),history8(),history9(),history10(),
                history11(),history12(),history13(),history14(),history15(),
                history16(),history17(),history18(),history19(),history20(),
                history21(),history22(),history23(),history24(),history25(),
                history26(),history27(),history28(),history29(),history30(),
                history41(),history42(),history43(),history44()
                )
        }

        fun history1(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_28112"
            h.nature = "Crue torrentielle du Rif Talon avec débordement dans le hameau de Malhivert."
            h.causes = "Pluies abondantes sur les falaises du Vercors."
            h.liste_c = "---- 38111(Claix) ---- " +
                    "->  Dégâts : O, Lieu dit: Malhivert" +
                    "Quelques maisons touchées (nb : ?) " +
                    "->  Perturbations : O, Lieu dit: Malhivert " +
                    "Légère coulée sur les voies d\'accès au hameau"
            h.date = "10/06/2007"
            h.longitude = 5.665113500
            h.latitude = 45.133056900
            return  h
        }

        fun history2(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_29553"
            h.nature = "Crue torrentielle affluent rive gauche du torrent de La PISSARDE."
            h.causes = "Pluies."
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Perturbations : O, Lieu dit: Combe du Cerisier\n" +
                    "Dépôts de matériaux sur la piste forestière (Plateau Saint Ange - Le Peuil)"
            h.date = "06/06/2010"
            h.longitude = 5.645992700
            h.latitude = 45.1044219
            return  h
        }

        fun history3(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_43644"
            h.nature = "Crue avec débordement de la Robine"
            h.causes = "Fortes précipitations sur sol gorgé d'eau. Obstruction d'un passage busé par des concrétions de tuf sous le chemin de Garretière."
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    "->  Perturbations : O, Inondation du chemin"
            h.date = "01/01/2018"
            h.longitude = 5.6629517
            h.latitude = 45.1169901
            return  h
        }

        fun history4(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47546"
            h.nature = "Crue importante du Lavanchon, avec ponts mis en charge.\n" +
                    "Pas de débordements.\n" +
                    "Interventions répétées des services communaux pour dégager les ponts (circulation interrompues)"
            h.causes = "Pluies durables sur une semaine, avec redoux et fonte de neige."
            h.liste_c = "---- 38524(Varces-Allières-et-Risset) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    "->  Perturbations : O, Ponts mobiles en charge. Interdits à la circulation"
            h.date = "24/12/1968"
            h.longitude = 5.6694918
            h.latitude = 45.0937470
            return  h
        }

        fun history5(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47546-2"
            h.nature = "Crue importante du Lavanchon, avec ponts mis en charge.\n" +
                    "Pas de débordements.\n" +
                    "Interventions répétées des services communaux pour dégager les ponts (circulation interrompues)"
            h.causes = "Pluies durables sur une semaine, avec redoux et fonte de neige."
            h.liste_c = "---- 38524(Varces-Allières-et-Risset) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    "->  Perturbations : O, Ponts mobiles en charge. Interdits à la circulation"
            h.date = "24/12/1968"
            h.longitude = 5.6724959
            h.latitude = 45.0999271
            return  h
        }

        fun history6(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47546-3"
            h.nature = "Crue importante du Lavanchon, avec ponts mis en charge.\n" +
                    "Pas de débordements.\n" +
                    "Interventions répétées des services communaux pour dégager les ponts (circulation interrompues)"
            h.causes = "Pluies durables sur une semaine, avec redoux et fonte de neige."
            h.liste_c = "---- 38524(Varces-Allières-et-Risset) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    "->  Perturbations : O, Ponts mobiles en charge. Interdits à la circulation"
            h.date = "24/12/1968"
            h.longitude = 5.6685047
            h.latitude = 45.0900811
            return  h
        }

        fun history7(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26955"
            h.nature = "Crue torrentielle du Lavanchon.\n" +
                    "Fort engravement du lit mineur.\n" +
                    "Ruptures de digues et débordements entre Brigaudière et Martinais d'en Bas."
            h.causes = "Pluies et redoux sur sols enneigés."
            h.liste_c = "---- 38524(Varces-Allières-et-Risset) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : O, Ruptures de digues entre Brigaudière et Martinais d'en Bas.\n" +
                    "Engravement des champs (10 000m3).\n" +
                    "->  Perturbations : N, \n" +
                    "---- 38436(Saint-Paul-de-Varces) -"
            h.date = "09/02/1955"
            h.longitude = 5.6686763
            h.latitude = 45.0913805
            return  h
        }

        fun history8(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26963"
            h.nature = "Crue torrentielle du torrent du Lavanchon"
            h.causes = "Pluies et redoux sur sols enneigés."
            h.liste_c = "---- 38436(Saint-Paul-de-Varces) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    "->  Perturbations : O, Pont de Meinget en charge.\n" +
                    "---- 38524(Varces-Allières-et-Risset) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : O, Lieu dit:"
            h.date = "15/02/1955"
            h.longitude = 5.6694394
            h.latitude = 45.0932680
            return  h
        }

        fun history9(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_43644"
            h.nature = "Crue avec débordement de la Robine"
            h.causes = "Fortes précipitations sur sol gorgé d'eau. Obstruction d'un passage busé par des concrétions de tuf sous le chemin de Garretière."
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    "->  Perturbations : O, Inondation du chemin"
            h.date = "01/01/2018"
            h.longitude = 5.6628349
            h.latitude = 45.1164573
            return  h
        }

        fun history10(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_43644"
            h.nature = "Lave torrentielle (essentiellement en blocs de calcaire Urgonien)."
            h.causes = "Le torrent, alimenté en matériaux par débourrage karstique (phénomène naturel) sous le Moucherotte a été réactivé par les fortes précipitations."
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "->  Dégâts : O, La lave torrentielle a traversé plusieurs sentiers et pistes forestières avant de terminer sa course dans une for├¬t privée.\n" +
                    "->  Perturbations : O, Inaccessibilité de deux pistes.\n" +
                    "Une pi"
            h.date = "03/06/2018"
            h.longitude = 5.6532971
            h.latitude = 45.1521334
            return  h
        }

        fun history11(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47545"
            h.nature = "Crue du Lavanchon.\n" +
                    "Débordements à Brigaudiere et aux Martinais d'En Haut.\n" +
                    "Crue en lien avec un très fort orage sur la partie haute du bassin versant, notamment le torrent des Coins"
            h.causes = "Orage violent."
            h.liste_c = "---- 38436(Saint-Paul-de-Varces) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : O, Champs inondés ├á Brigaudière\n" +
                    "->  Perturbations : N, \n" +
                    "---- 38524(Varces-Allières-et-Risset) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : O, Cham"
            h.date = "11/07/1951"
            h.longitude = 5.6679724
            h.latitude = 45.0896851
            return  h
        }

        fun history12(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47550"
            h.nature = "Crue du Lavanchon.\n" +
                    "Crue importante sur Claix. Submersion de la piste cyclable sous l'A51."
            h.causes = "Redoux pluvieux sur sols enneigés, associé à la tempête Aleanor.\n" +
                    "Successions de tempête sur la France depuis le 30 décembre 2017."
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    "->  Perturbations : O, Submersion de la piste cyclable sous l'A51."
            h.date = "01/01/2018"
            h.longitude = 5.6862143
            h.latitude = 45.1196669
            return  h
        }

        fun history13(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47575"
            h.nature = "Crue torrentielle"
            h.causes = "Fortes précipitations dans la nuit du 31 juillet au 1er aout."
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    "->  Perturbations : O, 3 passages à gué obstrués (route forestière de la Pissarde, piste de la Combe du Vent, sentier)"
            h.date = "31/07/2021"
            h.longitude = 5.6272408
            h.latitude = 45.0923448
            return  h
        }

        fun history14(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47785"
            h.nature = "Crue torrentielle du Rif Talon"
            h.causes = "Fortes précipitations du 27 au 29 décembre cumulées à la fonte des neiges"
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    ""
            h.date = "29/12/2021"
            h.longitude = 5.6803146
            h.latitude = 45.1199780
            return  h
        }

        fun history15(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47785-2"
            h.nature = "Crue torrentielle du Rif Talon"
            h.causes = "Fortes précipitations du 27 au 29 décembre cumulées à la fonte des neiges"
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    ""
            h.date = "29/12/2021"
            h.longitude = 5.54213600
            h.latitude = 45.1362434
            return  h
        }

        fun history16(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47551"
            h.nature = "Très forte crue avec apport conséquent et soudain de la branche du Rueffat à la suite d'une débâcle d'une retenue d'eau crée à l'amont d'un embâcle."
            h.causes = "Fortes pluies et redoux sur sols enneigés. \n" +
                    "Un glissement de terrain est à l'origine de la crue sur le Rueffat."
            h.liste_c = "---- 38436(Saint-Paul-de-Varces) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : O, Ponts rompus. \n" +
                    "Plusieurs propriétés du Bourg inondées.\n" +
                    "->  Perturbations : O, Ponts mis en charge et consolidés en urgence.\n" +
                    "école évacuée par précaution"
            h.date = "09/02/1955"
            h.longitude = 5.6303379
            h.latitude = 45.0835287
            return  h
        }

        fun history17(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47785"
            h.nature = "Crue torrentielle du Rif Talon"
            h.causes = "Fortes précipitations du 27 au 29 décembre cumulées à la fonte des neiges"
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "0 morts\n" +
                    "0 blessés\n" +
                    "-> Victimes : N, \n" +
                    "->  Dégâts : N, \n" +
                    ""
            h.date = "29/12/2021"
            h.longitude = 5.6836493
            h.latitude = 45.1183405
            return  h
        }

        fun history18(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_24719"
            h.nature = "Crue du torrent du Rif Talon"
            h.causes = "Fortes pluies pendant pusieurs jours ayant entra├«né une crue du Rif Talon\n" +
                    "- La buse au niveau de la rue de la Ronzy s'est bouchée. Le torrent a débordé inondant la rue de la Ronzy"
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Rue de la Ronzy\n" +
                    "Dépôt de matériaux à l'aval de la rue de la Ronzy dans propriété bâtie en rg. 300 m de route inondés.\n" +
                    "->  Perturbations : O, Lieu dit: Rue de la Ronzy\n" +
                    ""
            h.date = "16/11/2002"
            h.longitude = 5.6781086
            h.latitude = 45.1289183
            return  h
        }

        fun history19(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26980"
            h.nature = "Crue torrentielle du Rif Talon"
            h.causes = "Si le Rif Talon a quitté son lit, la faute en est à un riverain négligeant qui, depuis de longues années, l'a laissé encombré de brindilles, de bois coupé et même d'arbres entiers."
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Pont de Malhivert\n" +
                    "Pont de Malhivert obstrué par des graviers, rochers, morceaux de bois..., d'ou ravinement du chemin de Furonnières.\n" +
                    "Plusieurs champs ravagés.\n" +
                    "Vignes arrachées.\n" +
                    ""
            h.date = "14/01/1899"
            h.longitude = 5.6781086
            h.latitude = 45.1289183
            return  h
        }

        fun history26(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26980"
            h.nature = "Crue torrentielle du Rif Talon"
            h.causes = "Si le Rif Talon a quitté son lit, la faute en est à un riverain négligeant qui, depuis de longues années, l'a laissé encombré de brindilles, de bois coupé et même d'arbres entiers."
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Pont de Malhivert\n" +
                    "Pont de Malhivert obstrué par des graviers, rochers, morceaux de bois..., d'ou ravinement du chemin de Furonnières.\n" +
                    "Plusieurs champs ravagés.\n" +
                    "Vignes arrachées.\n" +
                    ""
            h.date = "14/01/1899"
            h.longitude = 5.6781086
            h.latitude = 45.1289183
            return  h
        }

        fun history20(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26981"
            h.nature = "Crue torrentielle du Rif Talon"
            h.causes = ""
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Plaine d'Allières et Risset à Claix\n" +
                    "Toute la plaine d'Allières et Risset à Claix inondée sur environ 3 km2\n" +
                    ""
            h.date = "10/1928"
            h.longitude = 5.6781086
            h.latitude = 45.1289183
            return  h
        }

        fun history21(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26986"
            h.nature = "Crue torrentielle de la Pissarde"
            h.causes = ""
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Pont de Savoyères\n" +
                    "Matériaux déposés sur le radier de la route communale (côte 1 050 m).\n" +
                    "Pont de Savoyères bouché.\n" +
                    "->  Perturbations : O, Lieu dit: Pont de Savoyères\n" +
                    ""
            h.date = "07/1989"
            h.longitude = 5.6385323
            h.latitude = 45.1050487
            return  h
        }

        fun history22(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26951"
            h.nature = "Crue torrentielle du Lavanchon"
            h.causes = ""
            h.liste_c = "---- 38436(Saint-Paul-de-Varces) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Le Lavanchon\n" +
                    "Pont démolli en partie par une crue subite, d'ou reconstruction nécessaire\n" +
                    ""
            h.date = "1931"
            h.longitude = 5.6681452
            h.latitude = 45.0920145
            return  h
        }

        fun history23(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_24744"
            h.nature = "Débordement du ruisseau du Bessay.\n" +
                    "Ruissellement aux Garlettes - Inondation par débordement de fossé."
            h.causes = "Très fortes pluies.\n" +
                    "Ruisseau de Bessay : ouvrage de rétention de matériaux et grilles aux entrées de canalisations totalement obstrués. Ces ouvrages étaient a priori mal entretenus."
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Le Prisme et Les Garlettes\n" +
                    "Route départementale 106 au Prisme.\n" +
                    "Une maison aux Garlettes.\n" +
                    "->  Perturbations : O, Lieu dit: Le Prisme et Les Garlettes\n" +
                    ""
            h.date = "16/11/2002"
            h.longitude = 5.6718430
            h.latitude = 45.1577034
            return  h
        }

        fun history24(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_27012"
            h.nature = "Ruissellement important et inondation aux Garlettes"
            h.causes = "Violent orage pendant 20 mn vers 17 H en amont des Garlettes"
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Les Garlettes\n" +
                    "Ecroulement d'une grange aux Garlettes.\n" +
                    "- 1 maison inondée au croisement Rue de Rampeau et Rue des Charrières.\n" +
                    "->  Perturbations : O, Lieu dit: Les Garlettes\n" +
                    "Route départementale rec"
            h.date = "07/1958"
            h.longitude = 5.6807147
            h.latitude = 45.1497970
            return  h
        }

        fun history25(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26981"
            h.nature = "Crue torrentielle du Rif Talon"
            h.causes = ""
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Plaine d'Allières et Risset à Claix\n" +
                    "Toute la plaine d'Allières et Risset à Claix inondée sur environ 3 km2"
            h.date = "10/1928"
            h.longitude = 5.6781217
            h.latitude = 45.128927
            return  h
        }

        fun history27(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26982"
            h.nature = "Crue torrentielle du Rif Talon"
            h.causes = ""
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Le Rif Talon\n" +
                    "Dégâts aux égoûts de la commune suite au débordement du Rif Talon"
            h.date = "4/1930"
            h.longitude = 5.6780954
            h.latitude = 45.1289096
            return  h
        }

        fun history28(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26983"
            h.nature = "Crue torrentielle du torrent du Rif Talon"
            h.causes = ""
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Malhivert\n" +
                    "Plancher de l'Ecole de Filles endommagé.\n" +
                    "10 m3 de remblai (engravement) dans la cour du groupe scolaire.\n" +
                    "2 ponts obstrués sur la route de Malhivert.\n" +
                    "Importants dégâts aux murs de clôture"
            h.date = "1932"
            h.longitude = 5.6781208
            h.latitude = 45.128909
            return  h
        }

        fun history29(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26984"
            h.nature = "Crue torrentielle du torrent du Rif Talon"
            h.causes = ""
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Route du Peuil\n" +
                    "Route du Peuil emportée\n" +
                    "->  Perturbations : O, Lieu dit: Route du Peuil"
            h.date = "15/2/1955"
            h.longitude = 5.6780963
            h.latitude = 45.1289276
            return  h
        }

        fun history30(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26987"
            h.nature = "Crue torrentielle du Rif Talon - Draye blanche"
            h.causes = ""
            h.liste_c = "----- 38111(Claix) ----"
            h.date = "2/1990"
            h.longitude = 5.6781349
            h.latitude = 45.1289357
            return  h
        }

        fun history31(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26988"
            h.nature = "Crue torrentielle du Rif Talon"
            h.causes = ""
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Carrefour de La Chièze\n" +
                    "Le CD 106d est coupé à la suite de l'obstruction complète du pont du CD.\n" +
                    "Les cailloux s'arrêtent vers la côte 255 m.\n" +
                    "Eau boueuse au-delà...\n" +
                    "->  Perturbations : O, Lieu dit:"
            h.date = "21/11/1992"
            h.longitude = 5.6780823
            h.latitude = 45.1289009
            return  h
        }

        fun history32(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26990"
            h.nature = "Débordement du torrent du Rif Talon"
            h.causes = "Orage violent"
            h.liste_c = "---- 38111(Claix) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Malhivert\n" +
                    "Déblaiement de la route et curage du torrent"
            h.date = "8/6/1996"
            h.longitude = 5.6781331
            h.latitude = 45.1288997
            return  h
        }

        fun history33(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26954"
            h.nature = "Crue torrentielle du torrent du Lavanchon"
            h.causes = ""
            h.liste_c = "---- 38436(Saint-Paul-de-Varces) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Aval St-Paul\n" +
                    "Plusieurs seuils endommagés à l'aval de St Paul.\n" +
                    "Clayonnages emportés.\n" +
                    "\n" +
                    "---- 38524(Varces-Allières-et-Risset) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Plaine entre Allières et"
            h.date = "10/12/1953"
            h.longitude = 5.6681583
            h.latitude = 45.0920232
            return  h
        }

        fun history34(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_26965"
            h.nature = "Crue torrentielle du torrent de la Suze"
            h.causes = ""
            h.liste_c = "---- 38524(Varces-Allières-et-Risset) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Torrent de la Suze\n" +
                    "Dégâts à l'usine et au stock de matériaux entreposés appartenant à M. RABERIN"
            h.date = "25/11/1928"
            h.longitude = 5.668132
            h.latitude = 45.0920058
            return  h
        }

        fun history35(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_45552"
            h.nature = "Plusieurs crues à fort charriage du Lavanchon en novembre et décembre 1935. Exhaussement du lit entre 60 et 80 cm sur 1 km de long entre St Paul de Varces et Varces. Des débordements localisés avec engravement des zones agricoles, mais aucun rupture"
            h.causes = "Pluies d'automne abondantes sur le quart sud-est de la France."
            h.liste_c = ""
            h.date = "1/11/1935"
            h.longitude = 5.6681583
            h.latitude = 45.0920232
            return  h
        }

        fun history36(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_47548"
            h.nature = "Crue du Lavanchon."
            h.causes = "Pluies abondantes et redoux sur sol enneigé."
            h.liste_c = ""
            h.date = "22/12/1991"
            h.longitude = 5.668132
            h.latitude = 45.0920058
            return  h
        }

        fun history37(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_27007"
            h.nature = "Lave torrentielle"
            h.causes = ""
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "->  Dégâts : O, Plusieurs sétérées de terre dévastées ; \"\"les ravines emportent souvent la moitié des fruits et quelques fois toute la terre\"\" (f° 68 v°)"
            h.date = "1682"
            h.longitude = 5.6718561
            h.latitude = 45.1577121
            return  h
        }

        fun history38(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_27014"
            h.nature = "Crue torrentielle du ruisseau de Besset"
            h.causes = "Pluies diluviennes"
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Ruisseau de Besset\n" +
                    "Rue de La Paix et place du village inondées\n" +
                    "->  Perturbations : O, Lieu dit: Ruisseau de Besset\n" +
                    "CD 106 coupé à la circulation"
            h.date = "24/10/1980"
            h.longitude = 5.6718298
            h.latitude = 45.1576947
            return  h
        }

        fun history39(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_27015"
            h.nature = "Crue torrentielle du ruisseau du Bessay"
            h.causes = ""
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Ruisseau du Bessay\n" +
                    "Quelques caves inondées.\n" +
                    "Rues et place défoncées par l'eau et engravées."
            h.date = "16/12/1981"
            h.longitude = 5.6718552
            h.latitude = 45.1576941
            return  h
        }

        fun history40(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_27452"
            h.nature = "Crue torrentielle du torrent de Bessay.\n" +
                    "Erosion importante dans la branche principale du torrent."
            h.causes = "Suite aux fortes pluies de l'été."
            h.liste_c = "---- 38486(Seyssins) ----"
            h.date = "1986"
            h.longitude = 5.6718307
            h.latitude = 45.1577127
            return  h
        }

        fun history41(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_27453"
            h.nature = "Lave torrentielle dans la branche principale du torrent de Bessay."
            h.causes = ""
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "->  Perturbations : O, Lieu dit: RD 106"
            h.date = "1989"
            h.longitude = 5.6718693
            h.latitude = 45.1577208
            return  h
        }

        fun history42(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_27454"
            h.nature = "Lave torrentielle dans la branche principale du torrent de Bessay."
            h.causes = ""
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "->  Perturbations : O, Lieu dit: Piste forestière\n" +
                    "Piste forestière et piste desservant l'antenne radio recouvertes de matériaux."
            h.date = "12/1991"
            h.longitude = 5.6718167
            h.latitude = 45.157686
            return  h
        }

        fun history43(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_27455"
            h.nature = "Lave torrentielle dans la branche principale du torrent de Bessay (comme en déc 1991)."
            h.causes = ""
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "->  Perturbations : O, Lieu dit: Piste forestière\n" +
                    "Piste forestière et piste desservant l'antenne radio recouvertes de matériaux."
            h.date = "7/1992"
            h.longitude = 5.6718675
            h.latitude = 45.1576848
            return  h
        }

        fun history44(): AlertingHistory {
            val h = AlertingHistory()
            h.id = "EV_30151"
            h.nature = "Débordement du ruisseau du Bessay.\n" +
                    "Ruissellement aux Garlettes - Inondation par débordement de fossé."
            h.causes = "Très fortes pluies.\n" +
                    "Ruisseau de Bessay : ouvrage de rétention de matériaux et grilles aux entrées de canalisations totalement obstrués. Ces ouvrages étaient a priori mal entretenus."
            h.liste_c = "---- 38486(Seyssins) ---- \n" +
                    "->  Dégâts : O, Lieu dit: Le Prisme et Les Garlettes\n" +
                    "Route départementale 106 au Prisme.\n" +
                    "Une maison aux Garlettes.\n" +
                    "->  Perturbations : O, Lieu dit: Le Prisme et Les Garlettes."
            h.date = "16/11/2002"
            h.longitude = 5.6807278
            h.latitude = 45.1498057
            return  h
        }
    }
}