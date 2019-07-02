package us.harvard.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*
import us.harvard.shoppinglist.R
import us.harvard.shoppinglist.ScrollingActivity
import us.harvard.shoppinglist.data.AppDatabase
import us.harvard.shoppinglist.data.Item
import us.harvard.shoppinglist.touch.ItemTouchHelperCallback
import java.util.*

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>, ItemTouchHelperCallback {

    var shopItems = mutableListOf<Item>()

    val context: Context
    constructor(context: Context, shopList: List<Item>) : super() {
        this.context = context
        shopItems.addAll(shopList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val shopView = LayoutInflater.from(context).inflate(
            R.layout.item_row, parent, false
        )
        return ViewHolder(shopView)
    }

    override fun getItemCount(): Int {
        return shopItems.size
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        var item = shopItems.get(holder.adapterPosition)

        holder.cbDone.text = item.toString()
        holder.cbDone.isChecked = item.done

        holder.btnDelete.setOnClickListener {
            removeItem(holder.adapterPosition)
        }

        holder.btnEdit.setOnClickListener {
            (context as ScrollingActivity).showEditItemDialog(
                item, holder.adapterPosition)
        }

        holder.cbDone.setOnClickListener{
            item.done = holder.cbDone.isChecked

            Thread{
                AppDatabase.getInstance(context).itemDao().updateItem(item)
            }.start()
        }
    }

    fun removeAll() {
        shopItems.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnDelete = itemView.btnDelete
        val cbDone = itemView.cbDone
        val btnEdit = itemView.btnEdit

    }

    fun addItem(item: Item){
        shopItems.add(item)

        notifyItemInserted(shopItems.lastIndex)
    }

    fun updateItem(item: Item, index : Int) {
        shopItems.set(index, item)
        notifyItemChanged(index)
    }

    fun removeItem(index: Int) {
        Thread {
            AppDatabase.getInstance(context).itemDao().deleteItem(shopItems.get(index))
            (context as ScrollingActivity).runOnUiThread {
                shopItems.removeAt(index)
                notifyItemRemoved(index)
            }
        }.start()
    }

    override fun onDismissed(position: Int) {
        removeItem(position)

    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(shopItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition,toPosition)

    }

}