package kr.purplebeen.noteapp.features.detailview

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail_view.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.features.edit.EditActivity
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition

class DetailViewActivity : AppCompatActivity() {
    val position : Int by lazy {
        intent.extras.getInt("position")
    }

    lateinit var note : Note
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
        note = pultusORM.find(Note())[position] as Note
        titleText.text = note.title
        contentText.text  = note.content
    }

    fun setListeners() {
        editButton.setOnClickListener{
            var newIntent  : Intent = Intent(this@DetailViewActivity, EditActivity::class.java)
            newIntent.putExtra("position", position)
            startActivity(newIntent)
        }
        deleteButton.setOnClickListener {
            val alert : AlertDialog = AlertDialog.Builder(this@DetailViewActivity).create()
            alert.setTitle("안내")
            alert.setMessage("정말로 삭제하시겠습니까?")
            alert.setButton(AlertDialog.BUTTON_POSITIVE, "확인") { _, i ->
                val condition: PultusORMCondition = PultusORMCondition.Builder()
                        .eq("noteId", note.noteId)
                        .build()
                pultusORM.delete(Note(), condition)
                Toast.makeText(applicationContext, "성공적으로 삭제되었습니다!", Toast.LENGTH_SHORT).show()
                finish()
            }
            alert.setButton(AlertDialog.BUTTON_NEGATIVE, "취소") { _, i ->
                alert.dismiss()
            }
            alert.show()
        }
   }

    override fun onResume() {
        super.onResume()
        loadData()
    }

}
