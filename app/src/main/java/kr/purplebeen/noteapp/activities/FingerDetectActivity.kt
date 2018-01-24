package kr.purplebeen.noteapp.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.github.ajalt.reprint.core.AuthenticationFailureReason
import com.github.ajalt.reprint.core.AuthenticationListener
import com.github.ajalt.reprint.core.Reprint
import kotlinx.android.synthetic.main.activity_finger_detect.*
import kr.purplebeen.noteapp.R

class FingerDetectActivity : AppCompatActivity() {
    var running : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger_detect)
        running = false
        fingerImage.setOnClickListener {
            if(running) {
                cancel()
            } else {
                start()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        cancel()
    }

    private fun start() {
        running = true
        statusText.text = "지문인식 대기중"
        fingerImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_close_black_24dp))
        startTraditional()
    }

    private fun startTraditional() {
        Reprint.authenticate(object : AuthenticationListener {
            override fun onSuccess(moduleTag: Int) {
                running = false
                Toast.makeText(applicationContext, "성공적으로 인증되었습니다!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@FingerDetectActivity, MainActivity::class.java))
            }

            override fun onFailure(failureReason: AuthenticationFailureReason?, fatal: Boolean, errorMessage: CharSequence?, moduleTag: Int, errorCode: Int) {
                Log.d("error", failureReason.toString())
                showError(failureReason!!, fatal, errorMessage!!, errorCode)
            }

        })
    }

    private fun cancel() {
        running = false
        fingerImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fingerprint_black_24dp))
        Reprint.cancelAuthentication()
    }
    private fun showError(failureReason: AuthenticationFailureReason, fatal : Boolean, errorMessage : CharSequence, errorCode : Int) {
        fingerImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fingerprint_black_24dp))
        fingerImage.setColorFilter(Color.parseColor("#FF0000"))
        var message : String = errorMessage.toString() +"\n 지문인식을 다시시도해주세요"
        statusText.text = message
        if(fatal) {
            fingerImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fingerprint_black_24dp))
            running = false
        }
    }
}
