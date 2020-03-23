package com.example.magicthegathering.ui.details.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.magicthegathering.R
import com.example.magicthegathering.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val constants =  Constants()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val nameCard = intent.getStringExtra(constants.nameCard)
        var imageUrl = intent.getStringExtra(constants.imageCard)

        if (imageUrl == "") {
            imageUrl = null
        }

        tv_name_set_detail.text = nameCard
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(img_card_detail)

        toolbar_image.setOnClickListener{
            finish()
        }
    }
}
