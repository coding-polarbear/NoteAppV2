package kr.purplebeen.noteapp.features.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.purplebeen.noteapp.Consts
import kr.purplebeen.noteapp.db.AppDatabase
import kr.purplebeen.noteapp.model.Note
import kr.purplebeen.noteapp.mvvm.SingleLiveEvent

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val addButtonCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    val listLoadedEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val clickedPostionLiveData: MutableLiveData<Int> = MutableLiveData()
    val db = Room.databaseBuilder(getApplication(),
            AppDatabase::class.java,
            Consts.DB_NAME)
            .build()
    var noteList: List<Note> = ArrayList()
        private set

    companion object {
        val TAG: String = MainViewModel::class.java.simpleName
    }
    val onItemClickListener: AdapterView.OnItemClickListener = AdapterView.OnItemClickListener { parent, vuew, position, id -> clickedPostionLiveData.value = position }

    @SuppressLint("CheckResult")
    fun loadNoteData() {
        db.noteDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({noteList ->
                    this.noteList = noteList
                    listLoadedEvent.call()
                }, {error ->
                    error.message?.let {
                        Log.e(TAG, it)
                    }
                })
    }

    fun onAddButtonClicked(view: View) {
        addButtonCallback.call()
    }
}