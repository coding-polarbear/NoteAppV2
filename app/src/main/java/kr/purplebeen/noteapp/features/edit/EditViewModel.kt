package kr.purplebeen.noteapp.features.edit

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.purplebeen.noteapp.Consts
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.db.AppDatabase
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent

class EditViewModel(application: Application): AndroidViewModel(application) {
    val db = Room.databaseBuilder(getApplication(),
            AppDatabase::class.java,
            Consts.DB_NAME)
            .build()

    private var currentPosition: Int = -1
    val editTitleField: ObservableField<String> = ObservableField()
    val editContentField: ObservableField<String> = ObservableField()
    val saveButtonClickCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val finishCallback: SingleLiveEvent<Void> = SingleLiveEvent()

    companion object {
        val TAG: String = EditViewModel::class.java.simpleName
    }

    @SuppressLint("CheckResult")
    fun loadData(position:Int) {
        currentPosition = position
        db.noteDao()
                .getNoteFromId(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({note ->
                    editTitleField.set(note.title)
                    editContentField.set(note.content)
                }, {error ->
                    error.message?.let {
                        Log.e(TAG, it)
                    }
                })
    }

    fun onClickSaveButton(view: View) {
        saveButtonClickCallback.call()
    }

    @SuppressLint("CheckResult")
    fun save(title: String, content: String) {
        db.noteDao()
                .getNoteFromId(currentPosition)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapCompletable { note ->
                    db.noteDao().insertNote(note)
                }.subscribe({
                    Toast.makeText(getApplication(), getApplication<Application>().resources.getString(R.string.edit_finish), Toast.LENGTH_SHORT).show()
                    finishCallback.call()
                }, {error ->
                    error.message?.let {
                        Log.e(TAG, it)
                    }
                })
    }
}