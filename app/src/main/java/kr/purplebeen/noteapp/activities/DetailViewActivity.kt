package kr.purplebeen.noteapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_view.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition

class DetailViewActivity : AppCompatActivity() {

    lateinit var pultusORM : PultusORM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)

        setORM()
        loadData()
    }



    fun setORM() {
        val appPath : String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("note.db", appPath)
    }

    fun loadData() {
        val position : Int = intent.extras.getInt("position")
        var note : Note = pultusORM.find(Note())[position] as Note
        titleText.text = note.title
        contentText.text  = note.content
    }

}
