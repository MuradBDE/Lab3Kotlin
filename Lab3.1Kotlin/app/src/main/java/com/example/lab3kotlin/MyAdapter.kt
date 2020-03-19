package com.example.lab3kotlin

import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_elements.view.*

class MyAdapter(val items : Cursor, val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_elements, parent, false))

    }
    // Прогрузка элементов в адаптере
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder?.number?.text =
        items.moveToPosition(position)
        var string = items.getString(0) + ": " + items.getString(1) + ": " + items.getString(2)
        holder?.number?.text = string
    }
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val number = view.item_textView
    }
}
