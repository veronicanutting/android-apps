package us.harvard.shopproject.data

import androidx.room.*

@Dao
interface ShopItemDAO {
    @Query("SELECT * FROM shoppingtable")
    fun getAllShopItems(): List<ShopItem>

    @Insert
    fun insertShopItem(shopItem: ShopItem) : Long

    @Delete
    fun deleteShopItem(shopItem: ShopItem)

    @Update
    fun updateShopItem(shopItem: ShopItem)


    @Query("DELETE FROM shoppingtable")
    fun deleteAllShopItems()
}
