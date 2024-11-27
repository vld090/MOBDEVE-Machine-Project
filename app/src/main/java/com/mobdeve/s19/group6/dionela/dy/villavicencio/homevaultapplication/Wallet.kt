package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Wallet : Fragment(), WalletAdapter.OnEllipsisClickListener {
    private lateinit var rvWallet: RecyclerView
    private lateinit var walletAdapter: WalletAdapter
    private lateinit var walletDBHelper: WalletDBHelper
    private var walletList: List<WalletItem> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvWallet = view.findViewById(R.id.rvWalletList)
        rvWallet.layoutManager = LinearLayoutManager(context)
        walletDBHelper = WalletDBHelper(requireContext())

        loadWalletItems()

        val fabAddReceiptBtn = view.findViewById<FloatingActionButton>(R.id.fbAddReceiptBtn)
        fabAddReceiptBtn.setOnClickListener {
            navigateToUploadForm()
        }
    }

    private fun loadWalletItems() {
        walletList = walletDBHelper.getAllWalletItems()
        walletAdapter = WalletAdapter(walletList, this)
        rvWallet.adapter = walletAdapter
    }

    private fun navigateToUploadForm() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flMainPage, UploadReceiptFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onEllipsisClick(walletItem: WalletItem) {
        val popupMenu = android.widget.PopupMenu(
            requireContext(),
            rvWallet.findViewHolderForAdapterPosition(walletList.indexOf(walletItem))?.itemView?.findViewById(
                R.id.ibEllipsis
            )
        )
        popupMenu.menuInflater.inflate(R.menu.ellipsis_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    // Delete the item from the database
                    val rowsDeleted = walletDBHelper.deleteWalletItem(walletItem.name)
                    if (rowsDeleted > 0) {
                        // Reload the updated list
                        loadWalletItems()
                    }
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

}
