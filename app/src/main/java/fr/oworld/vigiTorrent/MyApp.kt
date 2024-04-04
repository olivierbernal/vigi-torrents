package fr.oworld.vigiTorrent

import android.app.Activity
import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp

/**
 * Created by olivierbernal on 8/05/18 for oWorld Software
 */
class MyApp : Application() {

    private var mActivity: Activity? = null

    fun setActivity(mActivity: Activity) {
        this.mActivity = mActivity
    }

    companion object{
        public var CHANNEL_1_ID = "channel1"
        public var CHANNEL_2_ID = "channel2"

        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        context = this
    }
}


