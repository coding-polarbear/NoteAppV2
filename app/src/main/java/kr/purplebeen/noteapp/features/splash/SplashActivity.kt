package kr.purplebeen.noteapp.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.features.fingerdetect.FingerDetectActivity

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel : SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        observeViewModel()
        viewModel.excuteAppLaunch()
    }

    fun observeViewModel() {
        viewModel.nextView.observe(this, Observer{
            startActivity(Intent(this@SplashActivity, FingerDetectActivity::class.java))
        })
    }
}
