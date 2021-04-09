package kr.purplebeen.noteapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import kr.purplebeen.noteapp.R
import kr.purplebeen.noteapp.databinding.ItemDefaultNoteBinding
import kr.purplebeen.noteapp.model.Note

/**
 * Created by baehy on 2018. 1. 25..
 */
class ListAdapter(var context : Context, var noteList : List<Any>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var binding: ItemDefaultNoteBinding = ItemDefaultNoteBinding.inflate(LayoutInflater.from(context), parent, false)
        var note : Note = noteList[position] as Note
        binding.note = note
        return binding.root
    }

    override fun getItem(position: Int): Any {
        return noteList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return noteList.size
    }
}