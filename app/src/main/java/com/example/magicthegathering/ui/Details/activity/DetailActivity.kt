package com.example.magicthegathering.ui.details.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.magicthegathering.R
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.ui.favorites.fragment.viewmodel.FavoritesViewModel
import com.example.magicthegathering.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val constants =  Constants()
    private lateinit var favoritesViewModel: FavoritesViewModel
    lateinit var imageUrlPicasso: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initConf()

        val nameCard = intent.getStringExtra(constants.nameCard)
        var imageUrl = intent.getStringExtra(constants.imageCard)
        val setName = intent.getStringExtra(constants.setName)

        favoritesViewModel.search(nameCard).observe(this, Observer { card ->
            card?.let {
                if (card.name == nameCard) {
                    btn_favorites.text = resources.getText(R.string.btn_disfavor)
                }else {
                    btn_favorites.text = resources.getText(R.string.btn_favor)
                }
            }
        })

        if (imageUrl == "") {
            imageUrlPicasso = null.toString()
        }else {
            imageUrlPicasso = imageUrl
        }

        tv_name_set_detail.text = nameCard
        Picasso.get()
            .load(imageUrlPicasso)
            .placeholder(R.drawable.placeholder)
            .into(img_card_detail)

        toolbar_image.setOnClickListener{
            finish()
        }

        btn_favorites.setOnClickListener{
            if (btn_favorites.text == resources.getText(R.string.btn_favor)){
                favoritesViewModel.insert(card = Card(null, imageUrl, "", "", nameCard, setName))
                btn_favorites.text = resources.getText(R.string.btn_disfavor)
            }else {
                favoritesViewModel.delete(nameCard)
                btn_favorites.text = resources.getText(R.string.btn_favor)
            }
        }
    }

    private fun initConf() {
        favoritesViewModel = ViewModelProviders.of(this)
            .get(FavoritesViewModel::class.java)
    }
}
