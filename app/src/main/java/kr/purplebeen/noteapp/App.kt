package kr.purplebeen.noteapp

import android.app.Application
import android.util.Log
import com.github.ajalt.reprint.core.Reprint

/**
 * Created by baehy on 2018. 1. 24..
 */
class App : Application() {
    companion object {
        val TAG: String = "RePrint"
    }
    override fun onCreate() {
        super.onCreate()
        Reprint.initialize(this, object: Reprint.Logger {
            override fun logException(throwable: Throwable?, message: String?) {
                Log.e("RePrint", message,throwable)
            }

            override fun log(message: String?) {
                message?.let {
                    Log.d(TAG, it)
                }
            }
        })
    }
}