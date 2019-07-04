package us.harvard.shopproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shop_item_row.view.*
import us.harvard.shopproject.R
import us.harvard.shopproject.ScrollingActivity
import us.harvard.shopproject.data.AppDatabase
import us.harvard.shopproject.data.ShopItem

class ShopItemAdapter : RecyclerView.Adapter<ShopItemAdapter.ViewHolder>{

    var shopItems = mutableListOf<ShopItem>()

    val context : Context
    constructor(context: Context, shopList: List<ShopItem>) : super(){
        this.context = context
        shopItems.addAll(shopList)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbShopItemPurchased = itemView.cbShopItemPurchased
        val tvShopItemName = itemView.tvShopItemName
        val tvShopItemDescription = itemView.tvShopItemDescription
        val tvShopItemEstimatedPrice = itemView.tvShopItemEstimatedPrice
        val ivShopItemCategory = itemView.ivShopItemCategory
        val btnEditShopItem = itemView.btnEditShopItem
        val btnDeleteShopItem = itemView.btnDeleteShopItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val shopItemView = LayoutInflater.from(context).inflate(
            R.layout.shop_item_row, parent, false)
        return ViewHolder(shopItemView)
    }

    override fun getItemCount(): Int {
        return shopItems.size
    }

    var category : String = "food"

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var shopItem = shopItems.get(holder.adapterPosition)

        holder.cbShopItemPurchased.isChecked = shopItem.shopItemPurchased
        holder.tvShopItemName.text = shopItem.shopItemName
        holder.tvShopItemDescription.text = shopItem.shopItemDescription
        holder.tvShopItemEstimatedPrice.text = shopItem.shopItemEstimatedPrice
        category = shopItem.shopItemCategory

        if (category == "food") {
            holder.ivShopItemCategory.setImageResource(R.drawable.abc_ic_star_black_36dp)
        } else if (category == "alcohol") {
            holder.ivShopItemCategory.setImageResource(R.drawable.ic_mtrl_chip_close_circle)
        } else {
            holder.ivShopItemCategory.setImageResource(R.drawable.ic_mtrl_chip_checked_circle)
        }

        holder.btnDeleteShopItem.setOnClickListener{
            removeShopItem(holder.adapterPosition)
        }
        holder.btnEditShopItem.setOnClickListener{
            (context as ScrollingActivity).showEditShopItemDialog(
                shopItem, holder.adapterPosition)
        }
        holder.cbShopItemPurchased.setOnClickListener{
            shopItem.shopItemPurchased = holder.cbShopItemPurchased.isChecked

            Thread{
                AppDatabase.getInstance(context).shopItemDao()
                    .updateShopItem(shopItem)
            }.start()
        }
    }

    fun removeShopItem(index: Int) {
        Thread {
            AppDatabase.getInstance(context).shopItemDao().deleteShopItem(shopItems.get(index))
            (context as ScrollingActivity).runOnUiThread {
                shopItems.removeAt(index)
                notifyItemRemoved(index)
            }
        }.start()
    }

    fun removeAll() {
        shopItems.clear()
        notifyDataSetChanged()
    }

    fun addShopItem(shopItem: ShopItem){
        shopItems.add(shopItem)
        notifyItemInserted(shopItems.lastIndex)
    }

    fun updateShopItem(shopItem: ShopItem, index : Int) {
        shopItems.set(index, shopItem)
        notifyItemChanged(index)
    }

    // COULD ADD ON ITEM MOVED
}