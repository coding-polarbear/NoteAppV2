package kr.purplebeen.noteapp.features.fingerdetect

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.reprint.core.AuthenticationFailureReason
import com.github.ajalt.reprint.core.AuthenticationListener
import com.github.ajalt.reprint.core.Reprint
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent

class FingerDetectViewModel(application: Application): AndroidViewModel(application) {
    private var running:Boolean = false
    var isError = false

    var statusText: ObservableField<String> =  ObservableField()
    var fingerImageLiveData: MutableLiveData<Int> = MutableLiveData()
    var navigateToMainCallback: SingleLiveEvent<Void> = SingleLiveEvent()

    init {
        fingerImageLiveData.value = R.drawable.ic_fingerprint_black_24dp
    }

    fun onClickFingerImage(view: View) {
        if(running) {
            cancel()
        } else {
            start()
        }
    }

    private fun start() {
        running = true
        statusText.set("지문인식 대기중")
        fingerImageLiveData.value = R.drawable.ic_close_black_24dp
        startTraditional()
    }

    private fun startTraditional() {
        Reprint.authenticate(object : AuthenticationListener {
            override fun onSuccess(moduleTag: Int) {
                running = false
                Toast.makeText(getApplication(), getApplication<Application>().resources.getString(R.string.success_auth), Toast.LENGTH_SHORT).show()
                navigateToMainCallback.call()
            }

            override fun onFailure(failureReason: AuthenticationFailureReason?, fatal: Boolean, errorMessage: CharSequence?, moduleTag: Int, errorCode: Int) {
                Log.d("error", failureReason.toString())
                showError(failureReason!!, fatal, errorMessage!!, errorCode)
            }

        })
    }

    fun cancel() {
        running = false
        fingerImageLiveData.value = R.drawable.ic_fingerprint_black_24dp
        Reprint.cancelAuthentication()
    }
    private fun showError(failureReason: AuthenticationFailureReason, fatal : Boolean, errorMessage : CharSequence, errorCode : Int) {
        fingerImageLiveData.value = R.drawable.ic_fingerprint_black_24dp
        var message : String = "${errorMessage}\n ${getApplication<Application>().resources.getString(R.string.fail_auth)}"
        statusText.set(message)
        if(fatal) {
            fingerImageLiveData.value = R.drawable.ic_fingerprint_black_24dp
            running = false
        }
    }

}