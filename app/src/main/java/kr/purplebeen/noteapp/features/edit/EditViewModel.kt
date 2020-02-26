package kr.purplebeen.noteapp.features.edit

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import kotlinx.android.synthetic.main.activity_add.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.core.PultusORMUpdater

class EditViewModel(application: Application): AndroidViewModel(application) {
    lateinit var note : Note
    lateinit var pultusORM : PultusORM

    val editTitleField: ObservableField<String> = ObservableField()
    val editContentField: ObservableField<String> = ObservableField()
    val saveButtonClickCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val finishCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    var appPath:String = ""

    init {
        appPath = getApplication<Application>().filesDir.absolutePath
        pultusORM = PultusORM("note.db", appPath)
    }


    fun loadData(position:Int) {
        note = pultusORM.find(Note())[position] as Note
        editTitleField.set(note.title)
        editContentField.set(note.content)
    }

    fun onClickSaveButton(view: View) {
        saveButtonClickCallback.call()
    }

    fun save(title: String, content: String) {
        val condition: PultusORMCondition = PultusORMCondition.Builder()
                .eq("noteId", note.noteId)
                .build()
        val updater : PultusORMUpdater = PultusORMUpdater.Builder()
                .set("title", title)
                .set("content", content)
                .condition(condition)
                .build()
        pultusORM.update(Note(), updater)
        Toast.makeText(getApplication(), getApplication<Application>().resources.getString(R.string.save_finish), Toast.LENGTH_SHORT).show()
        finishCallback.call()
    }
}