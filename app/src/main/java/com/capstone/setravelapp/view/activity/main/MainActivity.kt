package com.capstone.setravelapp.view.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.capstone.setravelapp.R
import com.capstone.setravelapp.databinding.ActivityMainBinding
import com.capstone.setravelapp.view.fragment.favorite.FavoriteFragment
import com.capstone.setravelapp.view.fragment.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    val fragmentHome: Fragment = HomeFragment()
    val fragmentFav: Fragment = FavoriteFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = fragmentHome

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        buttoNavigation()
    }

    private fun buttoNavigation() {
        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentFav).hide(fragmentFav).commit()

        bottomNavigationView = mainBinding.navView
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.homepage -> {
                    callFragment(0, fragmentHome)
                }
                R.id.favorite -> {
                    callFragment(1, fragmentFav)
                }
            }
            false
        }



    }

    private fun callFragment(index : Int, fragment: Fragment) {
        menuItem = menu.get(index)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
}