package kr.purplebeen.noteapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_view.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition

class DetailViewActivity : AppCompatActivity() {
    val position : Int by lazy {
        intent.extras.getInt("position")
    }
    lateinit var pultusORM : PultusORM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)

        setORM()
        loadData()
        setListeners()
    }



    fun setORM() {
        val appPath : String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("note.db", appPath)
    }

    fun loadData() {

        var note : Note = pultusORM.find(Note())[position] as Note
        titleText.text = note.title
        contentText.text  = note.content
    }

    fun setListeners() {
        editButton.setOnClickListener{
            var newIntent  : Intent = Intent(this@DetailViewActivity, EditActivity::class.java)
            newIntent.putExtra("position", position)
            startActivity(newIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

}
