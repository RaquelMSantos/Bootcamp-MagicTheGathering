package com.example.magicthegathering.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Card {

    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String?= null

    @SerializedName("id")
    @Expose
    var id: String?= null

    @SerializedName("set")
    @Expose
    var set: String?= null

    @SerializedName("name")
    @Expose
    var name: String?= null

}