package data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {



    suspend fun addWish(wish: Wish) {
        wishDao.addWish(wish)
    }

    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish)
    }

    suspend fun deleteWish(wish: Wish) {
        wishDao.deleteWish(wish)
    }

    fun getWishById(id: Long): Flow<Wish> = wishDao.getWishById(id)

    fun getAllWishes(): Flow<List<Wish>> = wishDao.getAllWishes()
}