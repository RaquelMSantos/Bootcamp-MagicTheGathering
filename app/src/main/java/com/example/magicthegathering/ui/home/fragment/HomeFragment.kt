package com.example.magicthegathering.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.magicthegathering.R
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.ui.details.activity.DetailActivity
import com.example.magicthegathering.ui.home.adapter.CardAdapter
import com.example.magicthegathering.ui.home.adapter.HomeAdapter
import com.example.magicthegathering.ui.home.viewmodel.HomeViewModel
import com.example.magicthegathering.ui.home.viewmodel.HomeViewModelFactory
import com.example.magicthegathering.utils.CardOnClickListener
import com.example.magicthegathering.utils.CardRow
import com.example.magicthegathering.utils.Constants
import com.example.magicthegathering.utils.RowType
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), CardOnClickListener{

    private lateinit var cardAdapter: CardAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var homeViewModel: HomeViewModel
    private var cardArrayList = ArrayList<Card>()
    private val constants =  Constants()
    private var isLoading = false
    private var countPage = 1
    private var lastPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        observerViewModel()
    }

    private fun observerViewModel (){
        homeViewModel.homeLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cardArrayList = it as ArrayList<Card>
                val cardList = homeViewModel.sortCards(cardArrayList)
                progressBar(false)
                loadRecyclerView(cardList)
            }
        })
    }

    private fun initViewModel() {
        val viewModelFactory = HomeViewModelFactory()
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
        homeViewModel.getCards()
    }

    private fun loadRecyclerView(it: ArrayList<CardRow>) {
        gridLayoutManager = GridLayoutManager(this.context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (cardAdapter.getItemViewType(position)) {
                    RowType.CARD.ordinal -> 1
                    else -> gridLayoutManager.spanCount
                }
            }
        }
        cardAdapter = CardAdapter(it)
        rv_home.apply {
            layoutManager = gridLayoutManager
            adapter = cardAdapter

//            addOnScrollListener(object: RecyclerView.OnScrollListener(){
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                    val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
//                    val lastCompleteItem = gridLayoutManager.findLastCompletelyVisibleItemPosition()
//                    if (!isLoading) {
//                        if (lastCompleteItem == cardArrayList.size - 1) {
//                            countPage += 1
//                            isLoading = true
//                            lastPosition = cardArrayList.size + 1
//                            homeViewModel.getCards()
//                        }
//                    }
//                }
//            })
        }
    }

    private fun progressBar(status: Boolean) {
        if(status) {
            pb_home.visibility = View.VISIBLE
        }else {
            pb_home.visibility = View.GONE
        }
    }

//    private fun titleSet(status: Boolean){
//        if (status) {
//            tv_name_set_frag.visibility = View.VISIBLE
//            tv_name_set_frag.text = cardArrayList.get(0).setName
//        }else {
//            tv_name_set_frag.visibility = View.INVISIBLE
//        }
//    }

    override fun onClick(position: Int) {
        val intent = Intent(this.context, DetailActivity::class.java)
        val card = cardArrayList[position]
        var imageUrl = card.imageUrl
        intent.putExtra(constants.nameCard, card.name)
        if (imageUrl.isNullOrBlank()) {
           imageUrl = ""
        }
        intent.putExtra(constants.imageCard, imageUrl)
        startActivity(intent)
    }
}
