package com.example.magicthegathering.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magicthegathering.R
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.utils.CardOnClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_card.view.*

class HomeAdapter(private val listCards: MutableList<Card>, private val onClick: CardOnClickListener)
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = listCards[position]
        holder.bind(card)

        holder.itemView.setOnClickListener {
            onClick.onClick(position)
        }
    }

    override fun getItemCount(): Int = listCards.size

    class ViewHolder (inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_card, parent, false)){

        private var mCardImageView = itemView.img_card

        fun bind(card: Card) {
            //TODO - isolate the image lib in an extension, as a wrapper
            Picasso.get()
                .load(card.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(mCardImageView)
        }
    }
}