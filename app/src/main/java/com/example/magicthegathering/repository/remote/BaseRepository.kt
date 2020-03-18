package com.example.magicthegathering.repository.remote

import android.util.Log
import com.example.magicthegathering.network.Output
import retrofit2.Response
import java.io.IOException


open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, error: String): T? {
        val result = apiOutput(call, error)
        var output: T? = null
        when(result){
            is Output.Sucess ->
                output = result.data
            is Output.Error -> Log.e("Error", "The $error and the ${result.exception}")
        }
            return output
    }


    private suspend fun<T : Any> apiOutput(call: suspend()-> Response<T> , error: String) : Output<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            Output.Sucess(response.body()!!)
        else
            Output.Error(IOException("OOps .. Something went wrong due to  $error"))
    }
}