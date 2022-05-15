package com.example.rpncalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.StrictMath.pow
import java.util.*
import kotlin.math.abs

class ListAdapter(private val list: List<ListItemModel>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = list[position]
        val valueString = if (abs(listItem.value) >= pow(10.0, 16 - Preferences.precision.toDouble()))
            "%.${Preferences.precision}g".format(Locale.ENGLISH,listItem.value)
        else
            "%.${Preferences.precision}f".format(Locale.ENGLISH,listItem.value)
        holder.textView.text = valueString
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.listItemTextView)
    }
}