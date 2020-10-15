package com.example.magicthegathering.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.magicthegathering.network.models.Card

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    @Update
    fun updateCard(card: Card)

    @Query("DELETE FROM card_table where name = :nameCard")
    suspend fun deleteCard(nameCard: String)

    @Query("DELETE FROM card_table")
    suspend fun deleteAll()

    @Query("SELECT * from card_table")
    fun getCards(): LiveData<List<Card>>

    @Query("SELECT * FROM card_table where name = :nameCard")
    fun searchCard(nameCard: String): LiveData<Card>
}