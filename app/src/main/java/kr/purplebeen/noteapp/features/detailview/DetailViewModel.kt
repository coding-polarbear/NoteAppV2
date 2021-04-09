package kr.purplebeen.noteapp.features.detailview

import android.annotation.SuppressLint
import android.app.AlertDialog
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

class DetailViewModel(application:Application): AndroidViewModel(application) {
    val db = Room.databaseBuilder(getApplication(),
            AppDatabase::class.java,
            Consts.DB_NAME)
            .build()

    var titleField: ObservableField<String> = ObservableField()
    var contentField: ObservableField<String> = ObservableField()
    var navigateToEditCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    var finishCallback: SingleLiveEvent<Void> = SingleLiveEvent()
    private var currentPosition: Int = -1
    companion object {
        val TAG: String =  DetailViewModel::class.java.simpleName
    }

    @SuppressLint("CheckResult")
    fun loadData(position: Int) {
        currentPosition = position
        db.noteDao()
                .getNoteFromId(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ note ->
                    titleField.set(note.title)
                    contentField.set(note.content)
                }, {error ->
                    error.message?.let { Log.e(TAG, it) }
                })
    }

    fun onEditButtonClicked(view: View) {
        navigateToEditCallback.call()
    }

    fun onDeleteButtonClicked(view: View) {
        val alert : AlertDialog = AlertDialog.Builder(getApplication()).create()
        alert.setTitle(getApplication<Application>().resources.getString(R.string.inform))
        alert.setMessage(getApplication<Application>().resources.getString(R.string.really_delete))
        alert.setButton(AlertDialog.BUTTON_POSITIVE, getApplication<Application>().resources.getString(R.string.ok)) { _, i ->
            db.noteDao()
                    .deleteItem(currentPosition)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Toast.makeText(getApplication(), getApplication<Application>().resources.getString(R.string.delete_finish), Toast.LENGTH_SHORT).show()
                        finishCallback.call()
                    }
        }
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, getApplication<Application>().resources.getString(R.string.cancle)) { _, i ->
            alert.dismiss()
        }
        alert.show()

    }
}