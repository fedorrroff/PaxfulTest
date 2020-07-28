package com.example.paxfultest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.paxfultest.screens.jokes.JokesFragment
import com.example.paxfultest.screens.myjokes.MyJokesFragment
import com.example.paxfultest.screens.settings.SettingsFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureNavigationDrawer()
        configureToolbar()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.frame, JokesFragment.newInstance())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.empty_menu, menu)
        return true
    }

    private fun configureToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun configureNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation)

        navView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                val f: Fragment? = when (menuItem.itemId) {
                    R.id.my_jokes -> MyJokesFragment.newInstance()
                    R.id.jokes -> JokesFragment.newInstance()
                    R.id.settings -> SettingsFragment.newInstance()
                    else -> null
                }

                if (f != null) {
                    val transaction: FragmentTransaction =
                        supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.frame, f)
                    transaction.commit()
                    drawerLayout!!.closeDrawers()
                    return true
                }
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return true
    }
}