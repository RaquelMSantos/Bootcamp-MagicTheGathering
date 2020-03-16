package com.example.magicthegathering.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Card {

    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String?= null

    @SerializedName("id")
    @Expose
    var id: String?= null
}