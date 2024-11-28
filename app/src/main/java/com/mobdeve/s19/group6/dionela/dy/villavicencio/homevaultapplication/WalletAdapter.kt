package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.databinding.WalletItemBinding

class WalletAdapter(
    private var walletItems: List<WalletItem>,
    private val listener: OnEllipsisClickListener
) : RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    interface OnEllipsisClickListener {
        fun onEllipsisClick(walletItem: WalletItem)
    }

    inner class WalletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivReceiptImg: ImageView = itemView.findViewById(R.id.ivReceiptImg)
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val tvAssocItem: TextView = itemView.findViewById(R.id.tvAssocItem)
        val tvExpiryDate: TextView = itemView.findViewById(R.id.tvExpiryDate)
        val tvDateCreated: TextView = itemView.findViewById(R.id.tvDateCreated)
        val ibEllipsis: ImageButton = itemView.findViewById(R.id.ibEllipsis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wallet_item, parent, false)
        return WalletViewHolder(view)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val item = walletItems[position]
        holder.tvItemName.text = item.name
        holder.tvAssocItem.text = item.assocItemName
        holder.tvExpiryDate.text = "Expires on ${item.expiryDate}"
        holder.tvDateCreated.text = item.createdDate
        // Set image if available, or use a default
        holder.ivReceiptImg.setImageURI(Uri.parse(item.imageId)) // Replace with actual image loading logic if necessary

        // Handle ellipsis click
        holder.ibEllipsis.setOnClickListener {
            listener.onEllipsisClick(item)
        }
    }

    override fun getItemCount(): Int {
        return walletItems.size
    }

    fun updateData(newItems: List<WalletItem>) {
        walletItems = newItems
        notifyDataSetChanged()
    }
}
