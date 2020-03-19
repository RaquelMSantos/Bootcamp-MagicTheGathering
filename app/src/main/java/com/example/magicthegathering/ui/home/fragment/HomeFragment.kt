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

import com.example.magicthegathering.R
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.ui.details.activity.DetailActivity
import com.example.magicthegathering.ui.home.adapter.HomeAdapter
import com.example.magicthegathering.ui.home.viewmodel.HomeViewModel
import com.example.magicthegathering.ui.home.viewmodel.HomeViewModelFactory
import com.example.magicthegathering.utils.CardOnClickListener
import com.example.magicthegathering.utils.Constants
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

class HomeFragment : Fragment(), CardOnClickListener{

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var homeViewModel: HomeViewModel
    private var cardArrayList = ArrayList<Card>()
    private val constants =  Constants()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = HomeViewModelFactory()
        progressBar(true)
        titleSet(false)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
        homeViewModel.getCards()
        homeViewModel.homeLiveData.observe(viewLifecycleOwner, Observer {
            gridLayoutManager = GridLayoutManager(this.context, 3)
            if (it != null) {
                progressBar(false)
                homeAdapter = HomeAdapter(it, this@HomeFragment)
                cardArrayList = it as ArrayList<Card>
                titleSet(true)
                rv_home.apply {
                    layoutManager = gridLayoutManager
                    adapter = homeAdapter
                }
            }
        })
    }

    private fun progressBar(status: Boolean) {
        if(status) {
            pb_home.visibility = View.VISIBLE
        }else {
            pb_home.visibility = View.GONE
        }
    }

    private fun titleSet(status: Boolean){
        if (status) {
            tv_name_set_frag.visibility = View.VISIBLE
            tv_name_set_frag.text = cardArrayList.get(0).setName
        }else {
            tv_name_set_frag.visibility = View.INVISIBLE
        }
    }

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
