package kr.purplebeen.noteapp

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey

/**
 * Created by baehy on 2018. 1. 24..
 */

class Note {
    @PrimaryKey
    @AutoIncrement
    var noteId : Int = 0
    var title : String? = null
    var content : String? = null
}