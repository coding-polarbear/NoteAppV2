package kr.purplebeen.noteapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_default_note.view.*
import kr.purplebeen.noteapp.Note
import kr.purplebeen.noteapp.R

/**
 * Created by baehy on 2018. 1. 25..
 */
class ListAdapter(var context : Context, var noteList : List<Any>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var layoutView : View = inflater.inflate(R.layout.item_default_note, parent, false)

        var note : Note = noteList[position] as Note
        layoutView.titleText.text = note.title
        layoutView.dateText.text = note.date
        return layoutView
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