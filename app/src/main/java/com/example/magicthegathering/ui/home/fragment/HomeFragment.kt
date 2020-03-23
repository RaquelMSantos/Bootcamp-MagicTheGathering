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
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.ui.home.adapter.HomeAdapter
import com.example.magicthegathering.ui.home.viewmodel.HomeViewModel
import com.example.magicthegathering.ui.home.viewmodel.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var homeViewModel: HomeViewModel
    private var nameSet = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(progressBar = true, titleSet = false)
        initViewModel()
        observerViewModel()
    }

    private fun observerViewModel (){
        homeViewModel.homeLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                setupViews(progressBar = false, titleSet = true)
                nameSet = it.get(0).name
                loadRecyclerView(it)
            }
        })
    }

    private fun initViewModel() {
        val viewModelFactory = HomeViewModelFactory()
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
        homeViewModel.getCards()
    }

    private fun setupViews(progressBar: Boolean, titleSet: Boolean) {
        progressBar(progressBar)
        titleSet(titleSet)
    }

    private fun loadRecyclerView(it: MutableList<Card>) {
        gridLayoutManager = GridLayoutManager(this.context, 3)
        homeAdapter = HomeAdapter(it)
        rv_home.apply {
            layoutManager = gridLayoutManager
            adapter = homeAdapter
        }
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
            tv_name_set_frag.text = nameSet
        }else {
            tv_name_set_frag.visibility = View.INVISIBLE
        }
    }

}
