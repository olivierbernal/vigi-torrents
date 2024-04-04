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


class Alerting {
    var id: Int = 0
    var nameTorrent: String = ""
    var comment: String = ""

    var longitude: Double = 0.0
    var latitude: Double = 0.0

    var mediaPath: MutableList<String> = mutableListOf()
    var media: HashMap<Int, ByteArray> = hashMapOf()

    var date: DateTime = DateTime()

    var clarity: String = ""
    var speed: String = ""
    var waterHeight: String = ""
    var overflowWaterHeight: MutableList<String> = mutableListOf()
    var floatingElement: String = ""

    var eventType: String = ""

    var dateFormatter: SimpleDateFormat
    init {
        this.dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        this.dateFormatter.timeZone = TimeZone.getTimeZone("UTC")
    }

    constructor(){

    }

    constructor(list: MutableMap<String, Any>) {
        this.id = (list["id"] ?: 0).toString().toDouble().toInt()
        this.nameTorrent = (list["nomTorrent"] ?: "").toString()
        this.latitude = (list["latitude"] ?: "0").toString().toDouble()
        this.longitude = (list["longitude"] ?: "0").toString().toDouble()

        this.waterHeight = (list["hauteur"] ?: "").toString()
        if(this.waterHeight.contentEquals("||")) {
            this.overflowWaterHeight = this.waterHeight!!.split("||").toMutableList()
            this.waterHeight = ""
        } else {
            this.overflowWaterHeight = mutableListOf()
        }

        this.speed = (list["vitesse"] ?: "").toString()
        this.floatingElement = (list["transport"] ?: "").toString()
        this.clarity = (list["clarte"] ?: "").toString()
        this.comment = (list["commentaire"] ?: "").toString()
        this.date = DateTime(this.fromFirebase((list["heure"] ?: "").toString()))
        this.comment = (list["commentaire"] ?: "").toString()
        this.eventType = (list["evenement"] ?: "").toString()

        val photo_path = (list["media"] ?: "").toString().split("||").toMutableList()
        this.mediaPath = photo_path
        if(photo_path.isNotEmpty()) {
            for (path in photo_path) {
                FirebaseTools.getDatasFromStorage(FirebaseTools.kSignalementPath + this.id + "/" +  path) { data ->
                    if (data != null) {
                        this.media[photo_path.indexOf(path)] = data
                    }
                }
            }
        }
    }

    fun sendToServer(context: Context,
                     completionOnSuccess: ((key: String) -> Unit)? = null,
                     completionOnFailure: ((key: String) -> Unit)? = null) {
        val paramObject = JSONObject()
        paramObject.put("nomTorrent", nameTorrent ?: "")
        paramObject.put("latitude", latitude ?: 0.0)
        paramObject.put("longitude", longitude ?: 0.0)
        if ((waterHeight ?: "").isNotEmpty()){
            paramObject.put("hauteur", waterHeight ?: "")
        } else {
            paramObject.put("hauteur", overflowWaterHeight.joinToString("||","",""))
        }
        paramObject.put("vitesse", speed ?: "")
        paramObject.put("transport", floatingElement ?: "")
        paramObject.put("clarte", clarity ?: "")
        paramObject.put("commentaire", comment ?: "")
        paramObject.put("heure", toFirebase(date!!.toDate()) ?: "")
        paramObject.put("evenement", eventType.toString() ?: "")
        paramObject.put("media", mediaPath.joinToString("||","",""))

        val call = ApiClient.apiService.postSignalement(paramObject.toString())

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val map = Gson().fromJson(response.body().toString(), LinkedTreeMap<String, Any>().javaClass)
                    val a = Alerting(map)
                    this@Alerting.id = a.id

                    for (path in this@Alerting.mediaPath) {
                        FirebaseTools.saveOnStorage(this@Alerting.media[this@Alerting.mediaPath.indexOf(path)],
                            FirebaseTools.kSignalementPath + this@Alerting.id + "/" + path) {
                            completionOnSuccess!!("key")
                        }
                    }
                } else {
                    // Handle error
                    Toast.makeText(context, R.string.erreur_server, Toast.LENGTH_LONG)
                        .show()
                    completionOnFailure!!("key")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // Handle failure
                Toast.makeText(context, R.string.erreur_server, Toast.LENGTH_LONG)
                    .show()
                completionOnFailure!!("key")
            }
        })
    }

    fun getNewPhotoPath(): String {
        var lastPhoto = this.media.keys.count()
        lastPhoto = lastPhoto + 1

        val str = lastPhoto.toString()

        val randomPath = getRandomString(10) + "/"
        val p = randomPath + str + ".jpeg"
        return p
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun getColorIcon(context: Context): Bitmap {
        val drawable = ContextCompat.getDrawable(context, R.drawable.goutte)!!

        if(eventType != context.getString(R.string.alert_event_type1) ||
            overflowWaterHeight.isNotEmpty()) {
            DrawableCompat.setTint(drawable, context.getColor(R.color.red))
        } else if(waterHeight == context.getString(R.string.hauteur_proche_des_bords)) {
            DrawableCompat.setTint(drawable, context.getColor(R.color.orange))
        } else {
            DrawableCompat.setTint(drawable, context.getColor(R.color.blue_400))
        }

        return drawable.toBitmap(42,55)
    }

    companion object {
        fun getAllAlerting(context: Context, completionOnSuccess: ((listAlerting: HashMap<Int, Alerting>) -> Unit)){
            val call = ApiClient.apiService.getSignalements()

            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val post = response.body()
                        // Handle the retrieved post data
                        val arrayOfJSON = Gson().fromJson(response.body().toString(), mutableListOf<LinkedTreeMap<String, Any>>().javaClass)
                        var hashAlerting: HashMap<Int, Alerting> = hashMapOf()
                        for (json in arrayOfJSON) {
                            val a = Alerting(json)
                            hashAlerting[a.id] = a
                        }
                        completionOnSuccess(hashAlerting)
                    } else {
                        // Handle error
                        Toast.makeText(context, R.string.erreur_server, Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Handle failure
                    Toast.makeText(context, R.string.erreur_server, Toast.LENGTH_LONG)
                        .show()
                }
            })
        }



        fun getByteArray(activity: Activity, uri: Uri): ByteArray{
            val iStream: InputStream = activity.contentResolver!!.openInputStream(uri)!!
            val inputData: ByteArray = getBytes(iStream)
            return  inputData
        }

        @Throws(IOException::class)
        fun getBytes(inputStream: InputStream): ByteArray {
            val byteBuffer = ByteArrayOutputStream()
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)
            var len = 0
            while (inputStream.read(buffer).also { len = it } != -1) {
                byteBuffer.write(buffer, 0, len)
            }
            return byteBuffer.toByteArray()
        }
    }

    fun fromFirebase(dateTime: String) : Date? {
        if (dateTime == ""){
            return null
        }

        try {
            val date = dateFormatter.parse(dateTime)
            return date
        }
        catch(e: ParseException) {
            dateFormatter.applyPattern("dd/MM/yyyy")
            return Date()
        }
    }

    fun toFirebase(date: Date?) : String {
        if (date == null){
            return ""
        }

        return dateFormatter.format(date) as String
    }

}