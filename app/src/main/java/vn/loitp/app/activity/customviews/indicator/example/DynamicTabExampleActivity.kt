package vn.loitp.app.activity.customviews.indicator.example

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_dynamic_tab_example_layout.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import vn.loitp.app.R
import java.util.*

@LogTag("DynamicTabExampleActivity")
@IsFullScreen(false)
class DynamicTabExampleActivity : BaseFontActivity() {

    companion object {
        private val CHANNELS = arrayOf("CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT")
    }

    private val mDataList = ArrayList(mutableListOf(*CHANNELS))
    private val mExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    private var mCommonNavigator: CommonNavigator? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dynamic_tab_example_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager.adapter = mExamplePagerAdapter
        magicIndicator1.setBackgroundColor(Color.parseColor("#d43d3d"))
        mCommonNavigator = CommonNavigator(this)
        mCommonNavigator?.isSkimOver = true
        mCommonNavigator?.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = mDataList[index]
                clipPagerTitleView.textColor = Color.parseColor("#f2c4c4")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        }
        magicIndicator1.navigator = mCommonNavigator
        ViewPagerHelper.bind(magicIndicator1, viewPager)

        btRandomPage.setSafeOnClickListener {
            randomPage()
        }
    }

    private fun randomPage() {
        mDataList.clear()
        val total = Random().nextInt(CHANNELS.size)
        mDataList.addAll(mutableListOf(*CHANNELS).subList(0, total + 1))
        mCommonNavigator?.notifyDataSetChanged() // must call firstly
        mExamplePagerAdapter.notifyDataSetChanged()

        showShortInformation("${mDataList.size} page")
    }

}
