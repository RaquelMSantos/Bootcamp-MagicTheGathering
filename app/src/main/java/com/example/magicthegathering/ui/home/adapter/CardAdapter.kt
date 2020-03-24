package com.example.magicthegathering.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.magicthegathering.R
import com.example.magicthegathering.utils.CardRow
import com.example.magicthegathering.utils.DefaultViewHolder
import com.example.magicthegathering.utils.RowType
import com.squareup.picasso.Picasso

class CardAdapter (private var cardList: ArrayList<CardRow>):
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

//    fun updateCards(cards: ArrayList<CardRow>){
//        DiffUtil.calculateDiff(CardRowDiffCallback(cards, cardList),
//            false).dispatchUpdatesTo(this)
//        cardList = cards
//    }

    override fun getItemCount(): Int = cardList.size

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val cardRow: CardRow = cardList[position]
        if (cardRow.type == RowType.CARD) {
            val card = cardRow.card
            Picasso.get()
                .load(card?.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.getImage(R.id.img_card))
        }else {
            cardRow.header?.let { holder.setText(R.id.tv_name_set, it) }
        }
    }

    class CardRowDiffCallback(private val newRows: List<CardRow>, private val oldRows:
        List<CardRow>): DiffUtil.Callback(){

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRow = oldRows[oldItemPosition]
            val newRow = newRows[newItemPosition]
            return oldRow.type == newRow.type
        }

        override fun getOldListSize(): Int = oldRows.size
        override fun getNewListSize(): Int = newRows.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRow = oldRows[oldItemPosition]
            val newRow = newRows[newItemPosition]
            return oldRow.type == newRow.type
        }

    }
}