package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private val items: MutableList<CatalogItem>, // Mutable list to allow item removal
    private val dbHelper: DatabaseHelper // Pass DatabaseHelper for operations
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemImage: ImageView = view.findViewById(R.id.ivItemPic)
        private val itemName: TextView = view.findViewById(R.id.tvItemName)
        private val itemBrand: TextView = view.findViewById(R.id.tvBrand)
        private val itemCategory: TextView = view.findViewById(R.id.tvCategory)
        private val itemStock: TextView = view.findViewById(R.id.tvStock)
        private val editDeleteMenu: ImageView = view.findViewById(R.id.ivEditDelete)

        fun bind(item: CatalogItem) {
            itemImage.setImageResource(item.imageResId) // Set item image
            itemName.text = item.itemName
            itemBrand.text = item.brand ?: "No Brand"
            itemCategory.text = item.category
            itemStock.text = item.stock

            // Set up dropdown menu
            editDeleteMenu.setOnClickListener { showPopupMenu(it, item) }
        }

        private fun showPopupMenu(view: View, item: CatalogItem) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.item_options_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_delete -> {
                        deleteItem(item)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        private fun deleteItem(item: CatalogItem) {
            val result = dbHelper.deleteItem(item.itemName) // Delete item from the database
            if (result > 0) {
                val position = items.indexOf(item)
                items.removeAt(position) // Remove from the list
                notifyItemRemoved(position) // Update RecyclerView
                Toast.makeText(
                    itemView.context,
                    "Item '${item.itemName}' deleted successfully!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(itemView.context, "Failed to delete item.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_views, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
