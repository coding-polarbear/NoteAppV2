package kr.purplebeen.noteapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
data class Note(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int,

        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "content")
        var content: String,

        @ColumnInfo(name = "date")
        var date: String
) {
        constructor(title: String, content: String, date: String) : this(0, title, content, date)
}