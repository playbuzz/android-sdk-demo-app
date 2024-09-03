package com.exco.hostapp.integration.fragments.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.exco.hostapp.integration.R
import com.exco.player.views.ExCoAdapter

class RecyclerViewAdapter(private val dataList: List<ViewData>) : ExCoAdapter<ViewHolder>() {

    companion object {
        const val TYPE_TEXT = 1
        const val TYPE_EXO_PLAYER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_TEXT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false)
                TextViewHolder(view)
            }
            TYPE_EXO_PLAYER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exco_player, parent, false)
                ExCoPlayerViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        when (holder) {
            is TextViewHolder -> {
                data as ViewData.TextData
                holder.textView.text = data.text
            }
            is ExCoPlayerViewHolder -> {
                data as ViewData.ExCoPlayerData
                holder.exCoPlayerView.loadPlayer(data.configuration)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].type) {
            DataType.TEXT -> TYPE_TEXT
            DataType.EXO_PLAYER -> TYPE_EXO_PLAYER
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class TextViewHolder(itemView: View) : ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    inner class ExCoPlayerViewHolder(itemView: View) : ExCoViewHolder(itemView)
}