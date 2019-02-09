package kr.purplebeen.noteapp.features.splash

import android.os.Handler
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_detail_view.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent
import ninja.sakib.pultusorm.core.PultusORM

class SplashViewModel : ViewModel() {
    var nextView : SingleLiveEvent<Void> = SingleLiveEvent()

    fun excuteAppLaunch() {
        Handler().postDelayed({
            nextView.call()
        }, 3000)
    }
}