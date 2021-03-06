package com.example.magicthegathering.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magicthegathering.R
import com.example.magicthegathering.utils.CardOnClickListener
import com.example.magicthegathering.utils.CardRow
import com.example.magicthegathering.utils.DefaultViewHolder
import com.example.magicthegathering.utils.RowType
import com.squareup.picasso.Picasso

class CardAdapter (private var cardList: ArrayList<CardRow>, private val onClick: CardOnClickListener):
    RecyclerView.Adapter<DefaultViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val inflaterView: View = when(viewType) {
            RowType.CARD.ordinal -> layoutInflater.inflate(R.layout.item_card, parent, false)
            else -> layoutInflater.inflate(R.layout.item_header, parent, false)
        }
        return DefaultViewHolder(inflaterView)
    }

    override fun getItemViewType(position: Int) = cardList[position].type.ordinal

    override fun getItemCount(): Int = cardList.size

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val cardRow: CardRow = cardList[position]
        if (cardRow.type == RowType.CARD) {
            val card = cardRow.card
            if (card?.imageUrl.equals("")) {
                card?.imageUrl = null.toString()
            }
            Picasso.get()
                .load(card?.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.getImage(R.id.img_card))

            holder.itemView.setOnClickListener { onClick.onClick(position) }
        }else {
            cardRow.header?.let { holder.setText(R.id.tv_name_set, it) }
        }
    }

    internal fun setCards(cards: ArrayList<CardRow>){
        this.cardList = cards
        notifyDataSetChanged()
    }
}