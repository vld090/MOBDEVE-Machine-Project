package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Wallet : Fragment() {
    private lateinit var rvWallet: RecyclerView
    private lateinit var walletAdapter: WalletAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvWallet = view.findViewById(R.id.rvWalletList)
        rvWallet.layoutManager = LinearLayoutManager(context)

        val walletList = listOf(
            WalletItem(R.drawable.receipt1,"Receipt for Smart TV", "Smart TV","Samsung", "August 6, 2024", "August 6, 2023"),
            WalletItem(R.drawable.receipt2, "PS5 Receipt", "PS5","Sony", "August 6, 2024", "August 6, 2023"),
        )

        walletAdapter = WalletAdapter(walletList)
        rvWallet.adapter = walletAdapter
    }
}