package com.utils.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.R
import com.core.common.Constants

class BarUtils private constructor() {
    companion object {
        private const val DEFAULT_STATUS_BAR_ALPHA = 112
        private const val FAKE_STATUS_BAR_VIEW_TAG = "FAKE_STATUS_BAR_VIEW_TAG"
        private const val FAKE_TRANSLUCENT_VIEW_TAG = "FAKE_TRANSLUCENT_VIEW_TAG"
        private const val TAG_KEY_HAVE_SET_OFFSET = -123

        fun setColor(
                activity: Activity,
                @ColorInt color: Int
        ) {
            setColor(activity = activity, color = color, statusBarAlpha = DEFAULT_STATUS_BAR_ALPHA)
        }

        fun setColor(
                activity: Activity,
                @ColorInt color: Int,
                @IntRange(from = 0, to = 255) statusBarAlpha: Int
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                activity.window.statusBarColor = calculateStatusColor(color = color, alpha = statusBarAlpha)
            } else
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                val decorView = activity.window.decorView as ViewGroup
                val fakeStatusBarView = decorView.findViewWithTag<View>(FAKE_STATUS_BAR_VIEW_TAG)
                if (fakeStatusBarView != null) {
                    if (fakeStatusBarView.visibility == View.GONE) {
                        fakeStatusBarView.visibility = View.VISIBLE
                    }
                    fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
                } else {
                    decorView.addView(createStatusBarView(activity, color, statusBarAlpha))
                }
                setRootView(activity)
        }

        fun setColorForSwipeBack(
                activity: Activity,
                color: Int
        ) {
            setColorForSwipeBack(activity = activity, color = color, statusBarAlpha = DEFAULT_STATUS_BAR_ALPHA)
        }

        fun setColorForSwipeBack(
                activity: Activity,
                @ColorInt color: Int,
                @IntRange(from = 0, to = 255) statusBarAlpha: Int
        ) {
            val contentView = activity.findViewById<ViewGroup>(R.id.content)
            val rootView = contentView.getChildAt(0)
            val statusBarHeight = getStatusBarHeight(activity)
            if (rootView != null && rootView is CoordinatorLayout) {
                rootView.setStatusBarBackgroundColor(calculateStatusColor(color = color, alpha = statusBarAlpha))
            } else {
                contentView.setPadding(0, statusBarHeight, 0, 0)
                contentView.setBackgroundColor(calculateStatusColor(color = color, alpha = statusBarAlpha))
            }
            setTransparentForWindow(activity = activity)
        }

        fun setColorNoTranslucent(
                activity: Activity,
                @ColorInt color: Int
        ) {
            setColor(activity = activity, color = color, statusBarAlpha = 0)
        }

        @Deprecated("")
        fun setColorDiff(
                activity: Activity,
                @ColorInt color: Int
        ) {
            transparentStatusBar(activity)
            val contentView = activity.findViewById<ViewGroup>(R.id.content)
            val fakeStatusBarView = contentView.findViewWithTag<View>(FAKE_STATUS_BAR_VIEW_TAG)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(color)
            } else {
                contentView.addView(createStatusBarView(activity = activity, color = color))
            }
            setRootView(activity = activity)
        }

        fun setTranslucent(
                activity: Activity
        ) {
            setTranslucent(activity = activity, statusBarAlpha = DEFAULT_STATUS_BAR_ALPHA)
        }

        fun setTranslucent(
                activity: Activity,
                @IntRange(from = 0, to = 255) statusBarAlpha: Int
        ) {
            setTransparent(activity = activity)
            addTranslucentView(activity = activity, statusBarAlpha = statusBarAlpha)
        }

        fun setTranslucentForCoordinatorLayout(
                activity: Activity,
                @IntRange(from = 0, to = 255) statusBarAlpha: Int
        ) {
            transparentStatusBar(activity = activity)
            addTranslucentView(activity = activity, statusBarAlpha = statusBarAlpha)
        }

        fun setTransparent(
                activity: Activity
        ) {
            transparentStatusBar(activity = activity)
            setRootView(activity = activity)
        }

        @Deprecated("")
        fun setTranslucentDiff(activity: Activity) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setRootView(activity)
        }

        fun setColorForDrawerLayout(
                activity: Activity,
                drawerLayout: DrawerLayout,
                @ColorInt color: Int
        ) {
            setColorForDrawerLayout(activity = activity, drawerLayout = drawerLayout, color = color, statusBarAlpha = DEFAULT_STATUS_BAR_ALPHA)
        }

        fun setColorNoTranslucentForDrawerLayout(
                activity: Activity,
                drawerLayout: DrawerLayout,
                @ColorInt color: Int
        ) {
            setColorForDrawerLayout(activity = activity, drawerLayout = drawerLayout, color = color, statusBarAlpha = 0)
        }

        fun setColorForDrawerLayout(
                activity: Activity,
                drawerLayout: DrawerLayout,
                @ColorInt color: Int,
                @IntRange(from = 0, to = 255) statusBarAlpha: Int
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                activity.window.statusBarColor = Color.TRANSPARENT
            } else {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            val fakeStatusBarView = contentLayout.findViewWithTag<View>(FAKE_STATUS_BAR_VIEW_TAG)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(color)
            } else {
                contentLayout.addView(createStatusBarView(activity = activity, color = color), 0)
            }
            if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1)
                        .setPadding(contentLayout.paddingLeft, getStatusBarHeight(activity) + contentLayout.paddingTop,
                                contentLayout.paddingRight, contentLayout.paddingBottom)
            }
            setDrawerLayoutProperty(drawerLayout = drawerLayout, drawerLayoutContentLayout = contentLayout)
            addTranslucentView(activity = activity, statusBarAlpha = statusBarAlpha)
        }

        private fun setDrawerLayoutProperty(
                drawerLayout: DrawerLayout,
                drawerLayoutContentLayout: ViewGroup
        ) {
            val drawer = drawerLayout.getChildAt(1) as ViewGroup
            drawerLayout.fitsSystemWindows = false
            drawerLayoutContentLayout.fitsSystemWindows = false
            drawerLayoutContentLayout.clipToPadding = true
            drawer.fitsSystemWindows = false
        }

        @Deprecated("")
        fun setColorForDrawerLayoutDiff(
                activity: Activity,
                drawerLayout: DrawerLayout,
                @ColorInt color: Int
        ) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 生成一个状态栏大小的矩形
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            val fakeStatusBarView = contentLayout.findViewWithTag<View>(FAKE_STATUS_BAR_VIEW_TAG)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA))
            } else {
                contentLayout.addView(createStatusBarView(activity = activity, color = color), 0)
            }
            if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
            }
            setDrawerLayoutProperty(drawerLayout = drawerLayout, drawerLayoutContentLayout = contentLayout)
        }

        fun setTranslucentForDrawerLayout(
                activity: Activity,
                drawerLayout: DrawerLayout
        ) {
            setTranslucentForDrawerLayout(activity = activity, drawerLayout = drawerLayout, statusBarAlpha = DEFAULT_STATUS_BAR_ALPHA)
        }

        fun setTranslucentForDrawerLayout(
                activity: Activity,
                drawerLayout: DrawerLayout,
                @IntRange(from = 0, to = 255) statusBarAlpha: Int
        ) {
            setTransparentForDrawerLayout(activity = activity, drawerLayout = drawerLayout)
            addTranslucentView(activity = activity, statusBarAlpha = statusBarAlpha)
        }

        fun setTransparentForDrawerLayout(
                activity: Activity,
                drawerLayout: DrawerLayout
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                activity.window.statusBarColor = Color.TRANSPARENT
            } else {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
            }

            setDrawerLayoutProperty(drawerLayout = drawerLayout, drawerLayoutContentLayout = contentLayout)
        }

        @Deprecated("")
        fun setTranslucentForDrawerLayoutDiff(
                activity: Activity,
                drawerLayout: DrawerLayout
        ) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            contentLayout.fitsSystemWindows = true
            contentLayout.clipToPadding = true
            val vg = drawerLayout.getChildAt(1) as ViewGroup
            vg.fitsSystemWindows = false
            drawerLayout.fitsSystemWindows = false
        }

        fun setTransparentForImageView(
                activity: Activity,
                needOffsetView: View?
        ) {
            setTranslucentForImageView(activity = activity, statusBarAlpha = 0, needOffsetView = needOffsetView)
        }

        fun setTranslucentForImageView(
                activity: Activity,
                needOffsetView: View?
        ) {
            setTranslucentForImageView(activity = activity, statusBarAlpha = DEFAULT_STATUS_BAR_ALPHA, needOffsetView = needOffsetView)
        }

        fun setTranslucentForImageView(
                activity: Activity,
                @IntRange(from = 0, to = 255) statusBarAlpha: Int,
                needOffsetView: View?
        ) {
            setTransparentForWindow(activity = activity)
            addTranslucentView(activity = activity, statusBarAlpha = statusBarAlpha)
            if (needOffsetView != null) {
                val haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET)
                if (haveSetOffset != null && haveSetOffset as Boolean) {
                    return
                }
                val layoutParams = needOffsetView.layoutParams as MarginLayoutParams
                layoutParams.setMargins(
                        layoutParams.leftMargin,
                        layoutParams.topMargin + getStatusBarHeight(activity),
                        layoutParams.rightMargin,
                        layoutParams.bottomMargin
                )
                needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true)
            }
        }

        fun setTranslucentForImageViewInFragment(
                activity: Activity,
                needOffsetView: View?
        ) {
            setTranslucentForImageViewInFragment(activity = activity, statusBarAlpha = DEFAULT_STATUS_BAR_ALPHA, needOffsetView = needOffsetView)
        }

        fun setTransparentForImageViewInFragment(
                activity: Activity,
                needOffsetView: View?
        ) {
            setTranslucentForImageViewInFragment(activity = activity, statusBarAlpha = 0, needOffsetView = needOffsetView)
        }

        fun setTranslucentForImageViewInFragment(
                activity: Activity,
                @IntRange(from = 0, to = 255) statusBarAlpha: Int,
                needOffsetView: View?
        ) {
            setTranslucentForImageView(activity = activity, statusBarAlpha = statusBarAlpha, needOffsetView = needOffsetView)
            clearPreviousSetting(activity = activity)
        }

        fun hideFakeStatusBarView(
                activity: Activity
        ) {
            val decorView = activity.window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewWithTag<View>(FAKE_STATUS_BAR_VIEW_TAG)
            fakeStatusBarView?.visibility = View.GONE
            val fakeTranslucentView = decorView.findViewWithTag<View>(FAKE_TRANSLUCENT_VIEW_TAG)
            fakeTranslucentView?.visibility = View.GONE
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        private fun clearPreviousSetting(
                activity: Activity
        ) {
            val decorView = activity.window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewWithTag<View>(FAKE_STATUS_BAR_VIEW_TAG)
            if (fakeStatusBarView != null) {
                decorView.removeView(fakeStatusBarView)
                val rootView = (activity.findViewById<View>(R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
                rootView.setPadding(0, 0, 0, 0)
            }
        }

        private fun addTranslucentView(
                activity: Activity,
                @IntRange(from = 0, to = 255) statusBarAlpha: Int
        ) {
            val contentView = activity.findViewById<ViewGroup>(R.id.content)
            val fakeTranslucentView = contentView.findViewWithTag<View>(FAKE_TRANSLUCENT_VIEW_TAG)
            if (fakeTranslucentView != null) {
                if (fakeTranslucentView.visibility == View.GONE) {
                    fakeTranslucentView.visibility = View.VISIBLE
                }
                fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0))
            } else {
                contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha))
            }
        }

        private fun createStatusBarView(activity: Activity, @ColorInt color: Int, alpha: Int = 0): View {
            val statusBarView = View(activity)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
            statusBarView.layoutParams = params
            statusBarView.setBackgroundColor(calculateStatusColor(color = color, alpha = alpha))
            statusBarView.tag = FAKE_STATUS_BAR_VIEW_TAG
            return statusBarView
        }

        private fun setRootView(
                activity: Activity
        ) {
            val parent = activity.findViewById<ViewGroup>(R.id.content)
            var i = 0
            val count = parent.childCount
            while (i < count) {
                val childView = parent.getChildAt(i)
                if (childView is ViewGroup) {
                    childView.setFitsSystemWindows(true)
                    childView.clipToPadding = true
                }
                i++
            }
        }

        private fun setTransparentForWindow(
                activity: Activity
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.statusBarColor = Color.TRANSPARENT
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else
                activity.window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        private fun transparentStatusBar(
                activity: Activity
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                activity.window.statusBarColor = Color.TRANSPARENT
            } else {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        private fun createTranslucentStatusBarView(
                activity: Activity,
                alpha: Int
        ): View {
            val statusBarView = View(activity)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
            statusBarView.layoutParams = params
            statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
            statusBarView.tag = FAKE_TRANSLUCENT_VIEW_TAG
            return statusBarView
        }

        @JvmStatic
        fun getStatusBarHeight(
                context: Context
        ): Int {
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return context.resources.getDimensionPixelSize(resourceId)
        }

        private fun calculateStatusColor(
                @ColorInt color: Int,
                alpha: Int
        ): Int {
            if (alpha == 0) {
                return color
            }
            val a = 1 - alpha / 255f
            var red = color shr 16 and 0xff
            var green = color shr 8 and 0xff
            var blue = color and 0xff
            red = (red * a + 0.5).toInt()
            green = (green * a + 0.5).toInt()
            blue = (blue * a + 0.5).toInt()
            return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
        }

        fun setTransparentStatusBar(
                activity: Activity
        ) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        fun hideStatusBar(
                activity: Activity
        ) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        fun isStatusBarExists(
                activity: Activity?
        ): Boolean {
            if (activity == null) {
                return false
            }
            val params = activity.window.attributes
            return params.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != WindowManager.LayoutParams.FLAG_FULLSCREEN
        }

        fun getActionBarHeight(
                activity: Activity?
        ): Int {
            if (activity == null) {
                return Constants.NOT_FOUND
            }
            val tv = TypedValue()
            return if (activity.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
                TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
            } else {
                0
            }
        }

        fun showNotificationBar(
                context: Context,
                isSettingPanel: Boolean
        ) {
            val methodName = if (isSettingPanel) "expandSettingsPanel" else "expandNotificationsPanel"
            invokePanels(context, methodName)
        }

        fun hideNotificationBar(
                context: Context
        ) {
            val methodName = "collapsePanels"
            invokePanels(context, methodName)
        }

        private fun invokePanels(
                context: Context,
                methodName: String
        ) {
            try {
                val service = context.getSystemService("statusbar")
                val statusBarManager = Class.forName("android.app.StatusBarManager")
                val expand = statusBarManager.getMethod(methodName)
                expand.invoke(service)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
