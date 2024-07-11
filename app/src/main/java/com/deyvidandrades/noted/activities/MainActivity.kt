package com.deyvidandrades.noted.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.ViewPager2
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.adapters.AdaptadorFragmentos
import com.deyvidandrades.noted.assistentes.Persistencia
import com.deyvidandrades.noted.dialogs.DialogoConfiguracoes
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Persistencia.getInstance(this)

        if (Persistencia.isFirstTime) {
            startActivity(Intent(this, FirstTimeActivity::class.java))
            finish()
        }

        if (Persistencia.isPrivado && !Persistencia.auth) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        AppCompatDelegate.setDefaultNightMode(
            if (Persistencia.isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        configurarBottomNav()
        configurarTopBar()
    }

    private fun configurarTopBar() {
        val topBar: MaterialToolbar = findViewById(R.id.material_toolbar)

        topBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_1 -> {
                    val customBottomSheet = DialogoConfiguracoes()
                    customBottomSheet.show(supportFragmentManager, DialogoConfiguracoes().javaClass.name)
                    true
                }

                else -> false
            }
        }

    }

    private fun configurarBottomNav() {
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val badgeReview = bottomBar.getOrCreateBadge(R.id.item_2)

        if (Persistencia.getCategorias().size > 0) {
            badgeReview.isVisible = true
            badgeReview.number = Persistencia.getCategorias().size
        } else
            badgeReview.isVisible = false

        fun setVisibility(value: Int) {
            when (value) {
                1 -> {
                    bottomBar.selectedItemId = R.id.item_2
                }

                else -> {
                    bottomBar.selectedItemId = R.id.item_1
                }
            }
        }

        setVisibility(0)

        bottomBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    viewPager.currentItem = 0
                    true
                }

                R.id.item_2 -> {
                    viewPager.currentItem = 1
                    badgeReview.isVisible = false
                    true
                }

                else -> false
            }
        }

        viewPager.adapter = AdaptadorFragmentos(this)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setVisibility(position)
            }
        })
    }
}