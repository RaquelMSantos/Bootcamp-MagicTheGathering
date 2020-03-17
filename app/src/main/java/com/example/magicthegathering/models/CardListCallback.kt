package com.example.magicthegathering.models

import com.google.gson.annotations.SerializedName

data class CardListCallback (
    @SerializedName("cards")
    val listCard: List<Card>
)