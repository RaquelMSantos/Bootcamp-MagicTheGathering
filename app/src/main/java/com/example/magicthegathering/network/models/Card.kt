package com.example.magicthegathering.network.models

data class Card (
    val imageUrl: String,
    val id: String,
    val set: String,
    val name: String
)