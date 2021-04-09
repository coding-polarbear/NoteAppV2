package kr.purplebeen.noteapp.features.add

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.purplebeen.noteapp.Consts
import kr.purplebeen.noteapp.model.Note
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.db.AppDatabase
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(application: Application): AndroidViewModel(application) {

    val db = Room.databaseBuilder(getApplication(),
            AppDatabase::class.java,
            Consts.DB_NAME)
            .build()


    val saveButtonCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val finishCallback: SingleLiveEvent<Void> = SingleLiveEvent()

    fun onSaveButtonClicked(view: View) {
        saveButtonCallback.call()
    }

    @SuppressLint("CheckResult")
    fun save(title: String, content: String) {
        var dateFormat : DateFormat = SimpleDateFormat(getApplication<Application>().resources.getString(R.string.date_format))
        var note = Note(title, content, dateFormat.format(Date()))

        db.noteDao()
                .insertNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Toast.makeText(getApplication(), getApplication<Application>().resources.getString(R.string.save_finish), Toast.LENGTH_SHORT).show()
                    finishCallback.call()
                }

    }
}