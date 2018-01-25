package kr.purplebeen.noteapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.adapters.ListAdapter
import ninja.sakib.pultusorm.core.PultusORM

class MainActivity : AppCompatActivity() {
    val DURATION_TIME : Long = 2000L
    var prevPressTime : Long = 0L

    lateinit var  pultusORM : PultusORM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setORM()
        setListener()
        setListView()
    }

    fun setORM() {
        val appPath : String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("note.db", appPath)
    }

    fun setListener() {
        addButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddActivity::class.java))
        }
    }

    fun setListView() {
        var noteList : List<Any> = pultusORM.find(Note())
        listView.adapter = ListAdapter(applicationContext, noteList)
        listView.setOnItemClickListener { parent, view, position, id ->
            var intent : Intent = Intent(this@MainActivity, DetailViewActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - prevPressTime < DURATION_TIME) {
            moveTaskToBack(true)
            finish()
            android.os.Process.killProcess(android.os.Process.myPid())
        } else {
            prevPressTime = System.currentTimeMillis()
            Toast.makeText(applicationContext, "뒤로가기 버튼을 한번 더 누르면 애플리케이션이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        setListView()
    }
}
