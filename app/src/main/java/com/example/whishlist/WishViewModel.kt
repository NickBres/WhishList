package com.example.whishlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Wish
import data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val repository: WishRepository = Graph.wishRepository
): ViewModel() {

    var wishTitleState by mutableStateOf("")
    var wishDescState by mutableStateOf("")

    fun onWishTitleChanged(title: String){
        wishTitleState = title
    }

    fun onWishDescChanged(desc: String){
        wishDescState = desc
    }

    lateinit var getAllWishes: Flow<List<Wish>>

    init{
        viewModelScope.launch {
            getAllWishes = repository.getAllWishes()
        }
    }

    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWish(wish)
        }
    }

    fun updateWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWish(wish)
        }
    }

    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWish(wish)
        }
    }

    fun getWishById(id: Long): Flow<Wish>{
        return repository.getWishById(id)
    }

}