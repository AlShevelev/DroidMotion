package com.bitvale.droidmotion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bitvale.androidmotion.R
import com.bitvale.droidmotion.common.replaceFragmentInActivity
import com.bitvale.droidmotion.fragment.RecyclerFragment
import com.bitvale.droidmotion.listener.BottomNavigationViewListener
import com.bitvale.droidmotion.listener.OnBackPressedListener
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Alexander Kolpakov on 25.07.2018
 */
class MainActivity : AppCompatActivity(), BottomNavigationViewListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragment(RecyclerFragment.newInstance())
    }

    private fun setupFragment(fragment: Fragment) {
        supportFragmentManager.findFragmentById(R.id.fragment_container)    // todo [AS] supportFragmentManager.findFragmentById is always null
                ?: replaceFragmentInActivity(fragment, R.id.fragment_container)     // by tag
    }

    override fun hideBottomNavigationView() {
        if (bottom_navigation.translationY == 0f)
            bottom_navigation.animate()
                    .translationY(bottom_navigation.height.toFloat())
                    .setDuration(250)       // todo [AS] Why don't use standard system value for an animation?
                    .start()
    }

    override fun showBottomNavigationView() {
        if (bottom_navigation.translationY >= bottom_navigation.height.toFloat())
            bottom_navigation.animate()
                    .translationY(0f)
                    .setDuration(400)       // todo [AS] Why don't use standard system value for an animation?
                    .start()
    }

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments
        var proceedToSuper = true
        for (fragment in fragmentList) {
            if (fragment is OnBackPressedListener) {
                proceedToSuper = false
                (fragment as OnBackPressedListener).onBackPressed()
                // todo [AS] break ?
            }
        }
        if (proceedToSuper) super.onBackPressed()
    }
}
