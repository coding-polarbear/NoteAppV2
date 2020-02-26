package kr.purplebeen.noteapp.features.add

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import kr.purplebeen.noteapp.Consts
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent
import ninja.sakib.pultusorm.core.PultusORM
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(application: Application): AndroidViewModel(application) {
    var pultusORM: PultusORM
    var appPath:String = ""

    val saveButtonCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val finishCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    init {
        appPath = getApplication<Application>().filesDir.absolutePath
        pultusORM = PultusORM(Consts.DB_FILE, appPath)
    }

    fun onSaveButtonClicked(view: View) {
        saveButtonCallback.call()
    }

    fun save(title: String, content: String) {
        var note = Note()
        note.title = title
        note.content = content
        var dateFormat : DateFormat = SimpleDateFormat(getApplication<Application>().resources.getString(R.string.date_format))
        var date : Date = Date()
        note.date = dateFormat.format(date)
        pultusORM.save(note)
        Toast.makeText(getApplication(), getApplication<Application>().resources.getString(R.string.save_finish), Toast.LENGTH_SHORT).show()
        finishCallback.call()

    }
}