package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.databinding.WalletItemBinding

class WalletAdapter(private var walletList: List<WalletItem>): RecyclerView.Adapter<WalletAdapter.WalletListViewHolder>() {
    inner class WalletListViewHolder(val binding: WalletItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletListViewHolder {
        val binding = WalletItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WalletListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletListViewHolder, position: Int) {
        with(holder){
            with(walletList[position]) {
                binding.ivReceiptImg.setImageResource(imageId)
                binding.tvItemName.text = name
                binding.tvAssocItem.text = assocItemName
                binding.tvExpiry.text = expiryDate.toString()
                binding.tvDateCreated.text = createdDate.toString()
            }
        }
    }


    override fun getItemCount(): Int {
        return walletList.size
    }
}