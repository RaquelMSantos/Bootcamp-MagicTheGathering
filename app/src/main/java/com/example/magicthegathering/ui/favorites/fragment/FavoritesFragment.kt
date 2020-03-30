package com.example.magicthegathering.ui.favorites.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.magicthegathering.R
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.ui.details.activity.DetailActivity
import com.example.magicthegathering.ui.favorites.fragment.viewmodel.FavoritesViewModel
import com.example.magicthegathering.ui.adapter.CardAdapter
import com.example.magicthegathering.utils.CardOnClickListener
import com.example.magicthegathering.utils.CardRow
import com.example.magicthegathering.utils.Constants
import com.example.magicthegathering.utils.RowType
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(), CardOnClickListener {

    private var cardAdapter: CardAdapter? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private lateinit var favoritesViewModel: FavoritesViewModel
    private var cardListRow = ArrayList<CardRow>()
    private val constants =  Constants()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialConf()
    }

    private fun initialConf() {
        favoritesViewModel = ViewModelProviders.of(this)
            .get(FavoritesViewModel::class.java)

        favoritesViewModel.allCards.observe(viewLifecycleOwner, Observer { cards ->
            loadRecyclerView()
            cards?.let {
                cardListRow = favoritesViewModel.sortCards(it as ArrayList<Card>)
                cardListRow.sortBy { cardListRow.get(0).card?.setName }
                cardAdapter?.setCards(cardListRow)
            }
        })
    }

    private fun loadRecyclerView(){
        if (gridLayoutManager == null) {
            gridLayoutManager = GridLayoutManager(this.context, 3)
            gridLayoutManager?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (cardAdapter?.getItemViewType(position)) {
                        RowType.CARD.ordinal -> 1
                        else -> gridLayoutManager!!.spanCount
                    }
                }
            }
            rv_favorites.layoutManager = gridLayoutManager
        }

        if (cardAdapter == null) {
            cardAdapter = CardAdapter(cardListRow, this@FavoritesFragment)
            rv_favorites.adapter = cardAdapter
        }
    }

    override fun onClick(position: Int) {
        val intent = Intent(this.context, DetailActivity::class.java)
        val card = cardListRow[position]

        var imageUrl = card.card?.imageUrl

        intent.putExtra(constants.nameCard, card.card?.name)
        intent.putExtra(constants.setName, card.card?.setName)

        if (imageUrl.isNullOrBlank()) {
            imageUrl = ""
        }
        intent.putExtra(constants.imageCard, imageUrl)
        startActivity(intent)
    }
}
