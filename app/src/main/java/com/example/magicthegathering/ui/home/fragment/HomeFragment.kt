package com.example.magicthegathering.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magicthegathering.R
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.ui.details.activity.DetailActivity
import com.example.magicthegathering.ui.adapter.CardAdapter
import com.example.magicthegathering.ui.home.viewmodel.HomeViewModel
import com.example.magicthegathering.ui.home.viewmodel.HomeViewModelFactory
import com.example.magicthegathering.utils.CardOnClickListener
import com.example.magicthegathering.utils.CardRow
import com.example.magicthegathering.utils.Constants
import com.example.magicthegathering.utils.RowType
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), CardOnClickListener{

    private var cardAdapter: CardAdapter? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private lateinit var homeViewModel: HomeViewModel
    private var cardListRow = ArrayList<CardRow>()
    private val constants =  Constants()
    private var isLoading = false
    private var countPage = 0
    private var lastPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        observerViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.cancelRequest()
    }

    private fun observerViewModel (){
            homeViewModel.homeLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    loadRecyclerView()
                    val newCardList = homeViewModel.sortCards(it as ArrayList<Card>)
                    lastPosition = cardListRow.size + 1
                    cardListRow.addAll(newCardList)
                    updateList(lastPosition)
                    progressBar(false)
                }
            })
    }

    private fun updateList(lastPosition: Int) {
        if (cardAdapter?.itemCount!! > 0){
            rv_home.adapter?.notifyItemRangeInserted(lastPosition, cardListRow.size)
        }else {
            rv_home.adapter?.notifyDataSetChanged()
        }
        isLoading = false
    }

    private fun initViewModel() {
        val viewModelFactory = HomeViewModelFactory()
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)

            homeViewModel.getCards(countPage)

    }

    private fun loadRecyclerView() {
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
            rv_home.layoutManager = gridLayoutManager
        }
        if (cardAdapter == null) {
            cardAdapter = CardAdapter(cardListRow, this@HomeFragment)
            rv_home.adapter = cardAdapter
        }

        rv_home.apply {
            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastCompleteItem = gridLayoutManager.findLastCompletelyVisibleItemPosition()
                    if (!isLoading) {
                        if (lastCompleteItem == cardListRow.size -1) {
                            countPage += 1
                            isLoading = true
                            progressBar(true)
                            homeViewModel.getCards(countPage)
                        }
                    }
                }
            })
        }
    }

    private fun progressBar(status: Boolean) {
        pb_home_init.visibility = View.GONE
        if(status) {
            pb_home.visibility = View.VISIBLE
        }else {
            pb_home.visibility = View.GONE
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
