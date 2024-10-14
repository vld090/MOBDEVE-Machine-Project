package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeFragment(Home())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.iHome -> changeFragment(Home())
                R.id.iWallet -> changeFragment(Wallet())
                R.id.iNotifs -> changeFragment(NotifPage())
                R.id.iHistory -> changeFragment(LogHistory())

                else -> {

                }

            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun changeFragment(fragment : Fragment) {
        val fragManager = supportFragmentManager
        val fragTransaction = fragManager.beginTransaction()
        fragTransaction.replace(R.id.flMainPage, fragment)
        fragTransaction.commit()
    }
}
