package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

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

        val ibDropDown = view.findViewById<View>(R.id.llSortBy)
        ibDropDown.setOnClickListener {
            showSortMenu(it)
        }
    }

    private fun loadWalletItems(sortOption: String? = null) {
        walletList = walletDBHelper.getAllWalletItems()

        // Apply sorting based on the selected option
        walletList = when (sortOption) {
            "Item Name" -> walletList.sortedBy { it.name }
            "Associated Item" -> walletList.sortedBy { it.assocItemName }
            "Expiry Date" -> walletList.sortedBy { parseDate(it.expiryDate) }
            "Date Created" -> walletList.sortedBy { parseDate(it.createdDate) }
            else -> walletList
        }

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

    private fun showSortMenu(anchor: View) {
        val popupMenu = PopupMenu(requireContext(), anchor)
        popupMenu.menuInflater.inflate(R.menu.sort_wallet, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_item_name -> loadWalletItems("Item Name")
                R.id.sort_assoc_item -> loadWalletItems("Associated Item")
                R.id.sort_expiry_date -> loadWalletItems("Expiry Date")
                R.id.sort_created_date -> loadWalletItems("Date Created")
            }
            true
        }
        popupMenu.show()
    }

    private fun parseDate(dateString: String): Date {
        val format = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return format.parse(dateString) ?: Date(0) // Default to epoch if parsing fails
    }
}
