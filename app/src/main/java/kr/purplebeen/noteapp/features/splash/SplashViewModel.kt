package kr.purplebeen.noteapp.features.splash

import android.os.Handler
import androidx.lifecycle.ViewModel
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent

class SplashViewModel : ViewModel() {
    var nextView : SingleLiveEvent<Void> = SingleLiveEvent()

    fun excuteAppLaunch() {
        Handler().postDelayed({
            nextView.call()
        }, 3000)
    }
}