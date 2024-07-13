package us.harvard.shoppinglist.data

import androidx.room.*

@Dao
interface ItemDAO {
    @Query("SELECT * FROM itemtable")
    fun getAllItems(): List<Item>

    @Insert
    fun insertItem(item: Item) : Long

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Query("DELETE FROM itemtable")
    fun deleteAll()
}