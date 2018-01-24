package kr.purplebeen.noteapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kr.purplebeen.noteapp.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var handler : Handler  = Handler()
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, FingerDetectActivity::class.java))
        }, 3000)
    }
}
