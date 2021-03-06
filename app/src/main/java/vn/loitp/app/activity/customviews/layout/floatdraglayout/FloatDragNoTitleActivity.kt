package vn.loitp.app.activity.customviews.layout.floatdraglayout

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseActivity
import com.views.layout.floatdraglayout.DisplayUtil
import com.views.layout.floatdraglayout.FloatDragLayout
import vn.loitp.app.R

@LogTag("FloatDragNoTitleActivity")
@IsFullScreen(false)
class FloatDragNoTitleActivity : BaseActivity() {
    private var mDecorView: View? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_splash_v3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        mDecorView = window.decorView
        val rootView = mDecorView?.findViewById<ViewGroup>(android.R.id.content)
        val floatDragLayout = FloatDragLayout(this)
        floatDragLayout.setBackgroundResource(R.drawable.l_heart_icon)
        val size = DisplayUtil.dp2px(this, 100)
        val layoutParams = FrameLayout.LayoutParams(size, size)
        floatDragLayout.layoutParams = layoutParams
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        rootView?.addView(floatDragLayout, layoutParams)
        floatDragLayout.setOnClickListener {
            showShortInformation("Click on the hover and drag buttons")
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            mDecorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}
