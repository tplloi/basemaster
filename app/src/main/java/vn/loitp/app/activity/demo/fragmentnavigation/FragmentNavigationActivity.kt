package vn.loitp.app.activity.demo.fragmentnavigation

import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.google.android.material.navigation.NavigationView
import vn.loitp.app.R

@LogTag("FragmentNavigationActivity")
@IsFullScreen(false)
class FragmentNavigationActivity : BaseFontActivity(), NavigationView.OnNavigationItemSelectedListener {

    val T = "FragmentNavigationActivity"

    val navController: NavController
        get() = Navigation.findNavController(this, R.id.fragmentContainerView)

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fragment_navigation
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        logD("onNavigationItemSelected " + menuItem.title)
        return true
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        if (navHostFragment == null) {
            super.onBackPressed()
            return
        }
        val currentFragment = navHostFragment.childFragmentManager.fragments[0]
        if (currentFragment is OnBackPressedListener) {
            (currentFragment as OnBackPressedListener).onBackPressed()
        }
        /*else if (!getNavController().popBackStack()) {
            super.onBackPressed();
        }*/
    }

    fun popThisFragment() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }
}
