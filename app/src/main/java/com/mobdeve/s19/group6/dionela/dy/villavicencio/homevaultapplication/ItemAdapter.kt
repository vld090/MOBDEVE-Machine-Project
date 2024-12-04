package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ItemAdapter(
    private val items: MutableList<CatalogItem>, // Mutable list to allow item modification
    private val dbHelper: DatabaseHelper, // Pass DatabaseHelper for operations
    private val historyDBHelper: HistoryDBHelper
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemImage: ImageView = view.findViewById(R.id.ivItemPic)
        private val itemName: TextView = view.findViewById(R.id.tvItemName)
        private val itemBrand: TextView = view.findViewById(R.id.tvBrand)
        private val itemCategory: TextView = view.findViewById(R.id.tvCategory)
        private val itemStock: TextView = view.findViewById(R.id.tvStock)
        private val editDeleteMenu: ImageView = view.findViewById(R.id.ivEditDelete)
        private val btnAdd: Button = view.findViewById(R.id.btnAdd) // Add button
        private val btnDeduct: Button = view.findViewById(R.id.btnDeduct) // Deduct button

        fun bind(item: CatalogItem) {
            itemImage.setImageURI(Uri.parse(item.imageResId))
            itemName.text = item.itemName
            itemBrand.text = item.brand ?: "No Brand"
            itemCategory.text = item.category
            itemStock.text = item.stock.toString()

            // Set up Add and Deduct buttons
            btnAdd.setOnClickListener { incrementStock(item) }
            btnDeduct.setOnClickListener { decrementStock(item) }

            // Set up dropdown menu
            editDeleteMenu.setOnClickListener { showPopupMenu(it, item) }
        }

        private fun incrementStock(item: CatalogItem) {
            val currentStock = item.stock.toInt()
            val newStock = currentStock + 1

            val result = dbHelper.updateStock(item.itemName, newStock)
            if (result > 0) {
                item.stock = newStock // Update the local item
                itemStock.text = newStock.toString() // Reflect the change in UI

                val date = getCurrentDate()
                val newActivity = HistoryItem("Add Stock: ", item.itemName, date)
                historyDBHelper.insertHistoryItem(newActivity)

                Toast.makeText(itemView.context, "Stock added!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(itemView.context, "Failed to update stock.", Toast.LENGTH_SHORT).show()
            }
        }

        private fun decrementStock(item: CatalogItem) {
            val currentStock = item.stock.toInt()
            if (currentStock > 0) {
                val newStock = currentStock - 1

                val result = dbHelper.updateStock(item.itemName, newStock)
                if (result > 0) {
                    item.stock = newStock // Update the local item
                    itemStock.text = newStock.toString() // Reflect the change in UI

                    val date = getCurrentDate()
                    val newActivity = HistoryItem("Deduct Stock: ", item.itemName, date)
                    historyDBHelper.insertHistoryItem(newActivity)

                    Toast.makeText(itemView.context, "Stock deducted!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(itemView.context, "Failed to update stock.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(itemView.context, "Stock cannot be less than zero.", Toast.LENGTH_SHORT).show()
            }
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

                val date = getCurrentDate()
                val newActivity = HistoryItem("Delete Item: ", itemName.toString(), date)
                historyDBHelper.insertHistoryItem(newActivity)

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

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
