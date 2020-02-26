package kr.purplebeen.noteapp.features.main

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent
import ninja.sakib.pultusorm.core.PultusORM

class MainViewModel(application: Application): AndroidViewModel(application) {
    val addButtonCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val clickedPostionLiveData: MutableLiveData<Int> = MutableLiveData()
    lateinit var noteList: List<Any>
    private lateinit var pultusORM: PultusORM

    val onItemClickListener : AdapterView.OnItemClickListener = AdapterView.OnItemClickListener { parent, vuew, position, id -> clickedPostionLiveData.value = position }

    fun initPultusORM() {
        val appPath : String = getApplication<Application>().filesDir.absolutePath
        pultusORM = PultusORM("note.db", appPath)
        noteList = pultusORM.find(Note())

    }

    fun refreshData() {
        noteList = pultusORM.find(Note())
    }
    fun onAddButtonClicked(view: View) {
        addButtonCallback.call()
    }
}