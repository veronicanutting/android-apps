package us.harvard.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "itemtable")
data class Item (
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "done") var done: Boolean,
    @ColumnInfo(name = "itemtext") var itemText: String
) : Serializable