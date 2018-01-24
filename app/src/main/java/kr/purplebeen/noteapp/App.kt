package kr.purplebeen.noteapp

import android.app.Application
import android.util.Log
import com.github.ajalt.reprint.core.Reprint

/**
 * Created by baehy on 2018. 1. 24..
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Reprint.initialize(this, object: Reprint.Logger {
            override fun logException(throwable: Throwable?, message: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Log.e("RePrint", message,throwable)
            }

            override fun log(message: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Log.d("RePrint", message)
            }
        })
    }
}