package kr.purplebeen.noteapp.features.detailview

import android.app.AlertDialog
import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import kr.purplebeen.noteapp.Consts
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition

class DetailViewModel(application:Application): AndroidViewModel(application) {
    lateinit var note : Note
    var pultusORM : PultusORM
    var appPath: String = ""

    var titleField: ObservableField<String> = ObservableField()
    var contentField: ObservableField<String> = ObservableField()
    var navigateToEditCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    var finishCallback: SingleLiveEvent<Void> = SingleLiveEvent()

    init {
        appPath = getApplication<Application>().filesDir.absolutePath
        pultusORM = PultusORM(Consts.DB_FILE, appPath)
    }

    fun loadData(position: Int) {
        note = pultusORM.find(Note())[position] as Note
        titleField.set(note.title)
        contentField.set(note.content)
    }

    fun onEditButtonClicked(view: View) {
        navigateToEditCallback.call()
    }

    fun onDeleteButtonClicked(view: View) {
        val alert : AlertDialog = AlertDialog.Builder(getApplication()).create()
        alert.setTitle(getApplication<Application>().resources.getString(R.string.inform))
        alert.setMessage(getApplication<Application>().resources.getString(R.string.really_delete))
        alert.setButton(AlertDialog.BUTTON_POSITIVE, getApplication<Application>().resources.getString(R.string.ok)) { _, i ->
            val condition: PultusORMCondition = PultusORMCondition.Builder()
                    .eq("noteId", note.noteId)
                    .build()
            pultusORM.delete(Note(), condition)
            Toast.makeText(getApplication(), getApplication<Application>().resources.getString(R.string.delete_finish), Toast.LENGTH_SHORT).show()
            finishCallback.call()
        }
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, getApplication<Application>().resources.getString(R.string.cancle)) { _, i ->
            alert.dismiss()
        }
        alert.show()

    }
}