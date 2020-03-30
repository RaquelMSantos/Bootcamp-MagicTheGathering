package com.example.magicthegathering.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.magicthegathering.network.models.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Card::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "card_database"
                )
                    .addCallback(CardDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class CardDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.cardDao())
                }
            }
        }

        suspend fun populateDatabase(cardDao: CardDao){
            //cardDao.deleteAll()
            val card = Card(null, "",
                "1669af17-d287-5094-b005-4b143441442f", "10E", "Abundance", "Tenth Edition")
            cardDao.insertCard(card)
        }
    }
}