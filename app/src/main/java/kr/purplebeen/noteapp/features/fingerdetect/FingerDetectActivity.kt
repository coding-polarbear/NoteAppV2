package kr.purplebeen.noteapp.features.fingerdetect

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.databinding.ActivityFingerDetectBinding
import kr.purplebeen.noteapp.features.main.MainActivity

class FingerDetectActivity : AppCompatActivity() {
    private lateinit var mViewModel: FingerDetectViewModel
    private lateinit var mBinding: ActivityFingerDetectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_finger_detect)
        mViewModel = ViewModelProviders.of(this).get(FingerDetectViewModel::class.java)
        mBinding.viewModel = mViewModel
        observeViewModel()
    }

    override fun onPause() {
        super.onPause()
        mViewModel.cancel()
    }

    fun observeViewModel() {
        mViewModel.fingerImageLiveData.observe(this, Observer {value->
            mBinding.fingerImage.setImageDrawable(ContextCompat.getDrawable(this, value))
        })

        mViewModel.navigateToMainCallback.observe(this, Observer {
            startActivity(Intent(this@FingerDetectActivity, MainActivity::class.java))
        })
    }
}
