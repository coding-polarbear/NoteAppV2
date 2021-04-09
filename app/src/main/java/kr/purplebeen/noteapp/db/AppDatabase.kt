package kr.purplebeen.noteapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.purplebeen.noteapp.model.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}