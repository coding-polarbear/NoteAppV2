package kr.purplebeen.noteapp.features.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import ninja.sakib.pultusorm.core.PultusORM
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    lateinit var  pultusORM : PultusORM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setORM()
        setListeners()
    }

    fun setORM() {
        val appPath : String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("note.db", appPath)
    }

    fun setListeners() {
        saveButton.setOnClickListener {
            var note : Note = Note()
            note.title = editTitle.text.toString()
            note.content = editContent.text.toString()

            var dateFormat : DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            var date : Date = Date()
            note.date = dateFormat.format(date)
            Log.d("test", note.title.toString())
            pultusORM.save(note)

            Toast.makeText(applicationContext, "성공적으로 저장하였습니다!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
