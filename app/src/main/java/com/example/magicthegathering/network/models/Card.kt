package com.example.magicthegathering.network.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_table")
data class Card (
    @PrimaryKey
    var iddb:Long?,
    var imageUrl: String,
    val id: String,
    val set: String,
    val name: String,
    val setName: String
)