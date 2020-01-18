package com.nyayadhish.imagesearch.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nyayadhish.imagesearch.R
import com.nyayadhish.imagesearch.data.Items
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_image_item.view.*


abstract class SearchResultAdapter(var mList:List<Items>): RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
       return ViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.layout_image_item,parent,false)
       )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(mList[holder.adapterPosition].image.thumbnailLink).into(holder.itemView.iv_image)
        holder.itemView.setOnClickListener {
            onImageClick(mList[holder.adapterPosition],holder.itemView.iv_image)
        }
    }

    abstract fun onImageClick(
        image: Items,
        ivImage: ImageView
    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}