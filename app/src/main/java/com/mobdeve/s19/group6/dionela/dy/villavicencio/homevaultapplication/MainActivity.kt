package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
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

        if(!hasPermission()) {
            ActivityCompat.requestPermissions(
                this, required_permission, 0
            )
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private val required_permission = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private fun hasPermission(): Boolean {
        return required_permission.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun changeFragment(fragment : Fragment) {
        val fragManager = supportFragmentManager
        val fragTransaction = fragManager.beginTransaction()
        fragTransaction.replace(R.id.flMainPage, fragment)
        fragTransaction.commit()
    }
}