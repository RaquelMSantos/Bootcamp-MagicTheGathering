package com.example.magicthegathering.network

import java.lang.Exception

sealed class Output <out T: Any>{
    data class Sucess<out T: Any>(val data: T): Output<T>()
    data class Error(val exception: Exception) : Output<Nothing>()
}