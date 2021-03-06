package vn.loitp.app.activity.customviews.viewpager.viewpagerwithtablayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LDialogUtil
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.views.viewpager.viewpagertransformers.*
import kotlinx.android.synthetic.main.activity_viewpager_with_tablayout.*
import vn.loitp.app.R
import java.util.*

//https://github.com/geftimov/android-viewpager-transformers
@LogTag("ViewPagerWithTabLayoutActivity")
@IsFullScreen(false)
class ViewPagerWithTabLayoutActivity : BaseFontActivity() {
    private val resList: MutableList<Int> = ArrayList()

    companion object {
        private const val AccordionTransformer = "AccordionTransformer"
        private const val BackgroundToForegroundTransformer = "BackgroundToForegroundTransformer"
        private const val CubeInTransformer = "CubeInTransformer"
        private const val CubeOutTransformer = "CubeOutTransformer"
        private const val DefaultTransformer = "DefaultTransformer"
        private const val DepthPageTransformer = "DepthPageTransformer"
        private const val DrawFromBackTransformer = "DrawFromBackTransformer"
        private const val FlipHorizontalTransformer = "FlipHorizontalTransformer"
        private const val FlipVerticalTransformer = "FlipVerticalTransformer"
        private const val ForegroundToBackgroundTransformer = "ForegroundToBackgroundTransformer"
        private const val ParallaxPageTransformer = "ParallaxPageTransformer"
        private const val RotateUpTransformer = "RotateUpTransformer"
        private const val RotateDownTransformer = "RotateDownTransformer"
        private const val StackTransformer = "StackTransformer"
        private const val TabletTransformer = "TabletTransformer"
        private const val ZoomInTransformer = "ZoomInTransformer"
        private const val ZoomOutSlideTransformer = "ZoomOutSlideTransformer"
        private const val ZoomOutTranformer = "ZoomOutTranformer"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_viewpager_with_tablayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (i in 0..19) {
            resList.add(LStoreUtil.randomColor)
        }
        viewPager.adapter = SlidePagerAdapter()
        tabLayout.setupWithViewPager(viewPager)
        LUIUtil.changeTabsFont(tabLayout, Constants.FONT_PATH)
        btAnim.setOnClickListener {
            showDialogAnim()
        }
    }

    private fun showDialogAnim() {
        val stringList: MutableList<String?> = ArrayList()
        stringList.add(AccordionTransformer)
        stringList.add(BackgroundToForegroundTransformer)
        stringList.add(CubeInTransformer)
        stringList.add(CubeOutTransformer)
        stringList.add(DefaultTransformer)
        stringList.add(DepthPageTransformer)
        stringList.add(DrawFromBackTransformer)
        stringList.add(FlipHorizontalTransformer)
        stringList.add(FlipVerticalTransformer)
        stringList.add(ForegroundToBackgroundTransformer)
        //stringList.add(ParallaxPageTransformer);
        stringList.add(RotateDownTransformer)
        stringList.add(RotateUpTransformer)
        stringList.add(StackTransformer)
        stringList.add(TabletTransformer)
        stringList.add(ZoomInTransformer)
        stringList.add(ZoomOutSlideTransformer)
        stringList.add(ZoomOutTranformer)
        val arr = stringList.toTypedArray()

        LDialogUtil.showDialogList(
                context = this,
                title = "Select",
                arr = arr,
                onClick = { position ->
                    showShortInformation("Click position " + position + ", item: " + arr[position])
                    when (stringList[position]) {
                        AccordionTransformer -> viewPager.setPageTransformer(true, AccordionTransformer())
                        BackgroundToForegroundTransformer -> viewPager.setPageTransformer(true, BackgroundToForegroundTransformer())
                        CubeInTransformer -> viewPager.setPageTransformer(true, CubeInTransformer())
                        CubeOutTransformer -> viewPager.setPageTransformer(true, CubeOutTransformer())
                        DefaultTransformer -> viewPager.setPageTransformer(true, DefaultTransformer())
                        DepthPageTransformer -> viewPager.setPageTransformer(true, DepthPageTransformer())
                        DrawFromBackTransformer -> viewPager.setPageTransformer(true, DrawFromBackTransformer())
                        FlipHorizontalTransformer -> viewPager.setPageTransformer(true, FlipHorizontalTransformer())
                        FlipVerticalTransformer -> viewPager.setPageTransformer(true, FlipVerticalTransformer())
                        ForegroundToBackgroundTransformer -> viewPager.setPageTransformer(true, ForegroundToBackgroundTransformer())
                        RotateDownTransformer -> viewPager.setPageTransformer(true, RotateDownTransformer())
                        RotateUpTransformer -> viewPager.setPageTransformer(true, RotateUpTransformer())
                        StackTransformer -> viewPager.setPageTransformer(true, StackTransformer())
                        TabletTransformer -> viewPager.setPageTransformer(true, TabletTransformer())
                        ZoomInTransformer -> viewPager.setPageTransformer(true, ZoomInTransformer())
                        ZoomOutSlideTransformer -> viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
                        ZoomOutTranformer -> viewPager.setPageTransformer(true, ZoomOutTranformer())
                    }
                }
        )
    }

    private inner class SlidePagerAdapter : PagerAdapter() {
        @SuppressLint("SetTextI18n")
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
//            val res = resList[position]
            val inflater = LayoutInflater.from(this@ViewPagerWithTabLayoutActivity)
            val layout = inflater.inflate(R.layout.item_photo_slide_iv, collection, false) as ViewGroup
            val imageView = layout.findViewById<ImageView>(R.id.imageView)
            if (position % 2 == 0) {
                imageView.setImageResource(R.drawable.iv)
            } else {
                imageView.setImageResource(R.drawable.logo)
            }
            val tv = layout.findViewById<TextView>(R.id.textView)
            tv.text = position.toString() + "/" + resList.size
            collection.addView(layout)
            return layout
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            if (view is View) {
                collection.removeView(view)
            }
        }

        override fun getCount(): Int {
            return resList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "Page Title $position"
        }
    }
}
