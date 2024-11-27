package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Wallet : Fragment() {
    private lateinit var rvWallet: RecyclerView
    private lateinit var walletAdapter: WalletAdapter
    private lateinit var walletDatabaseHelper: WalletDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        walletDatabaseHelper = WalletDatabaseHelper(requireContext())

        rvWallet = view.findViewById(R.id.rvWalletList)
        rvWallet.layoutManager = LinearLayoutManager(context)

        val walletList = walletDatabaseHelper.getAllWallets() // Fetch wallet items from database
        walletAdapter = WalletAdapter(walletList)
        rvWallet.adapter = walletAdapter

        val fabAddReceiptBtn = view.findViewById<FloatingActionButton>(R.id.fbAddReceiptBtn)
        fabAddReceiptBtn.setOnClickListener {
            navigateToUploadForm()
        }
    }

    private fun navigateToUploadForm() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flMainPage, UploadReceiptFragment())
            .addToBackStack(null)
            .commit()
    }
}
