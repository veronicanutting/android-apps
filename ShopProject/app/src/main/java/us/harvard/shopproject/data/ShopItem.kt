package us.harvard.shopproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shoppingtable")
data class ShopItem (
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "purchased") var shopItemPurchased: Boolean,
    @ColumnInfo(name = "name")var shopItemName: String,
    @ColumnInfo(name = "description")var shopItemDescription: String,
    @ColumnInfo(name = "price")var shopItemEstimatedPrice: String,
    @ColumnInfo(name = "category")var shopItemCategory: String
) : Serializable