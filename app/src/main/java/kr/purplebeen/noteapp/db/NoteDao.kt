package kr.purplebeen.noteapp.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import kr.purplebeen.noteapp.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getAll(): Flowable<List<Note>>

    @Query("SELECT * FROM NOTE WHERE id = :id")
    fun getNoteFromId(id: Int): Flowable<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note): Completable

    @Query("DELETE FROM Note")
    fun deleteAll(): Completable

    @Query("DELETE FROM NOTE WHERE id = :id")
    fun deleteItem(id: Int): Completable
}