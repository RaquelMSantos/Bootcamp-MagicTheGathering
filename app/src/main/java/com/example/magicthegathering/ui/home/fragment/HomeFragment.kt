package com.example.magicthegathering.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import com.example.magicthegathering.R
import com.example.magicthegathering.ui.home.adapter.HomeAdapter
import com.example.magicthegathering.viewmodel.CardViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var cardViewModel: CardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)
        cardViewModel.homePagedList?.observe(viewLifecycleOwner, Observer {  cardList ->
            homeAdapter.submitList(cardList)
        })

        gridLayoutManager = GridLayoutManager(this.context, 3)
        homeAdapter = HomeAdapter()
        rv_home.apply {
            layoutManager = gridLayoutManager
            adapter = homeAdapter
        }
    }

}
