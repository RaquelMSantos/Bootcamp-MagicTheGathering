package com.example.magicthegathering.utils

import com.example.magicthegathering.network.models.Card

enum class RowType {
    CARD,
    HEADER
}

data class CardRow(var type: RowType, var card: Card?, var header: String?)

