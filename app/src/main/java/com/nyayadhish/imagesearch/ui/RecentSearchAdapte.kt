package com.nyayadhish.imagesearch.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyayadhish.imagesearch.R
import kotlinx.android.synthetic.main.layout_recent_search.view.*

abstract class RecentSearchAdapte(var mSet: MutableSet<String>?) :
    RecyclerView.Adapter<RecentSearchAdapte.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_recent_search,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mSet?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_recent_search.text = mSet?.elementAt(holder.adapterPosition)
        holder.itemView.setOnClickListener {
            mSet?.elementAt(holder.adapterPosition)?.let { it1 -> onSearchItemClick(it1) }
        }
    }

    abstract fun onSearchItemClick(searchItem: String)


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}