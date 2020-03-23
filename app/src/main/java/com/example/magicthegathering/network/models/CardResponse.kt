package com.example.magicthegathering.network.models

import com.google.gson.annotations.SerializedName

data class CardResponse (
    @SerializedName("cards")
    val listCard: List<Card>
)