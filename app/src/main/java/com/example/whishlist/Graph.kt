package com.example.whishlist

import android.content.Context
import androidx.room.Room
import data.WishDatabase
import data.WishRepository

object Graph {
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(database.wishDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(
            context,
            WishDatabase::class.java,
            "wish_db"
        ).build()
    }
}