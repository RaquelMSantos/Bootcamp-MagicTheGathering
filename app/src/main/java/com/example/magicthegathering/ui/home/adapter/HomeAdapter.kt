package com.example.magicthegathering.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.magicthegathering.R
import com.example.magicthegathering.models.Card
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_card.view.*

class HomeAdapter: PagedListAdapter<Card, HomeAdapter.ViewHolder> (diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = getItem(position)
        if (card != null) {
            holder.bind(card)
        }
    }

    companion object {
        private val diffCallback = object: DiffUtil.ItemCallback<Card>(){
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem.equals(newItem)
            }
        }

    class ViewHolder (inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_card, parent, false)){

        private var mCardImageView = itemView.img_card

        fun bind(card: Card) {
            Picasso.get()
                .load(card.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(mCardImageView)
        }
    }
}