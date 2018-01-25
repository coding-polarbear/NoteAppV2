package kr.purplebeen.noteapp.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.core.PultusORMUpdater

/**
 * Created by baehy on 2018. 1. 25..
 */
class EditActivity : AppCompatActivity() {
    val position : Int by lazy {
        intent.extras.getInt("position")
    }
    lateinit var note : Note
    lateinit var pultusORM : PultusORM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setORM()
        loadData()
        setListeners()
    }

    fun setORM() {
        val appPath : String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("note.db", appPath)
    }

    fun loadData() {
        note = pultusORM.find(Note())[position] as Note
        editTitle.setText(note.title)
        editContent.setText(note.content)
    }

    fun setListeners() {
        saveButton.setOnClickListener {
            val condition: PultusORMCondition = PultusORMCondition.Builder()
                    .eq("noteId", note.noteId)
                    .build()
            val updater : PultusORMUpdater = PultusORMUpdater.Builder()
                    .set("title", editTitle.text.toString())
                    .set("content", editContent.text.toString())
                    .condition(condition)
                    .build()
            pultusORM.update(Note(), updater)
            Toast.makeText(applicationContext, "성공적으로 업데이트 되었습니다!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}