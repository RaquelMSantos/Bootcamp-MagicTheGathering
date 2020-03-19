package com.example.magicthegathering.network.models

import com.google.gson.annotations.SerializedName

data class SetResponse (
    @SerializedName("sets")
    val listSetModels: List<SetModel>
)