package com.core.helper.gallery.slide

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.R
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.core.utilities.LAnimationUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.task.AsyncTaskDownloadImage
import com.views.layout.floatdraglayout.DisplayUtil
import com.views.viewpager.viewpagertransformers.ZoomOutSlideTransformer
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_slide.*

class GalleryCoreSlideActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTransparentStatusNavigationBar()
            LUIUtil.setMargins(view = rlControl, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = DisplayUtil.getNavigationBarHeight(activity))
        } else {
            LUIUtil.setMargins(view = rlControl, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = DisplayUtil.getStatusHeight(activity))
        }
        val bkgRootView = intent.getIntExtra(Constants.BKG_ROOT_VIEW, Constants.NOT_FOUND)
        if (bkgRootView == Constants.NOT_FOUND) {
            rootView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary))
        } else {
            rootView.setBackgroundResource(bkgRootView)
        }

        val slidePagerAdapter = SlidePagerAdapter(supportFragmentManager)
        viewPager.adapter = slidePagerAdapter
        //LUIUtil.setPullLikeIOSHorizontal(viewPager)
        viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
        val photoID = intent.getStringExtra(Constants.SK_PHOTO_ID) ?: ""
        val position = instance.getPosition(photoID)

        viewPager.currentItem = position
        btDownload.setOnClickListener {
            instance.getPhoto(viewPager.currentItem)?.urlO?.let {
                AsyncTaskDownloadImage(applicationContext, it).execute()
            }
        }
        btShare.setOnClickListener {
            instance.getPhoto(viewPager.currentItem)?.urlO?.let {
                LSocialUtil.share(activity = activity, msg = it)
            }
        }
        btReport.setOnClickListener {
            LSocialUtil.sendEmail(context = activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_slide
    }

    private inner class SlidePagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            val frmIvSlideCore = FrmIvSlideCore()
            val bundle = Bundle()
            bundle.putInt(Constants.SK_PHOTO_PISITION, position)
            frmIvSlideCore.arguments = bundle
            return frmIvSlideCore
        }

        override fun getCount(): Int {
            return instance.size
        }
    }

    fun toggleDisplayRlControl() {
        if (isRlControlShowing) {
            hideRlControl()
        } else {
            showRlControl()
        }
    }

    private var isRlControlShowing = true
    private fun showRlControl() {
        rlControl.visibility = View.VISIBLE
        isRlControlShowing = true
        LAnimationUtil.play(view = rlControl, techniques = Techniques.SlideInUp)
    }

    private fun hideRlControl() {
        LAnimationUtil.play(view = rlControl, techniques = Techniques.SlideOutDown, callback = object : LAnimationUtil.Callback {
            override fun onCancel() {}
            override fun onEnd() {
                rlControl.visibility = View.INVISIBLE
                isRlControlShowing = false
            }

            override fun onRepeat() {}
            override fun onStart() {}
        })
    }
}