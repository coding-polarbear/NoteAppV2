package kr.purplebeen.noteapp.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.features.fingerdetect.FingerDetectActivity

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
