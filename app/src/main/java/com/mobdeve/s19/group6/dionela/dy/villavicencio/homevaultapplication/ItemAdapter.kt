package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<CatalogItem>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemImage: ImageView = view.findViewById(R.id.ivItemPic)
        private val itemName: TextView = view.findViewById(R.id.tvItemName)
        private val itemBrand: TextView = view.findViewById(R.id.tvBrand)
        private val itemCategory: TextView = view.findViewById(R.id.tvCategory)
        private val itemStock: TextView = view.findViewById(R.id.tvStock)

        fun bind(item: CatalogItem) {
            itemImage.setImageResource(item.imageResId)
            itemName.text = item.itemName
            itemBrand.text = item.brand
            itemCategory.text = item.category
            itemStock.text = item.stock
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
