package com.example.magicthegathering.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SetModel {
    @SerializedName("code")
    @Expose
    var code: String?= null

    @SerializedName("name")
    @Expose
    var name: String?= null

    @SerializedName("releaseDate")
    @Expose
    var date: String?= null
}