package vn.loitp.app.activity.customviews.indicator.example

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_no_tab_only_indicator_example_layout.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView
import vn.loitp.app.R

@LogTag("NoTabOnlyIndicatorExampleActivity")
@IsFullScreen(false)
class NoTabOnlyIndicatorExampleActivity : BaseFontActivity() {

    companion object {
        private val CHANNELS = arrayOf("CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "NOUGAT", "DONUT")
    }

    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_no_tab_only_indicator_example_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
    }

    private fun initMagicIndicator1() {
        magicIndicator1.setBackgroundColor(Color.LTGRAY)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return DummyPagerTitleView(context)
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val linePagerIndicator = LinePagerIndicator(context)
                val lineHeight = context.resources.getDimension(R.dimen.small_navigator_height)
                linePagerIndicator.lineHeight = lineHeight
                linePagerIndicator.setColors(Color.parseColor("#40c4ff"))
                return linePagerIndicator
            }
        }
        magicIndicator1.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator1, viewPager)
    }

    private fun initMagicIndicator2() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return DummyPagerTitleView(context)
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val triangularPagerIndicator = TriangularPagerIndicator(context)
                triangularPagerIndicator.isReverse = true
                val smallNavigatorHeight = context.resources.getDimension(R.dimen.small_navigator_height)
                triangularPagerIndicator.lineHeight = UIUtil.dip2px(context, 2.0)
                triangularPagerIndicator.triangleHeight = smallNavigatorHeight.toInt()
                triangularPagerIndicator.lineColor = Color.parseColor("#e94220")
                return triangularPagerIndicator
            }
        }
        magicIndicator2.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator2, viewPager)
    }

}
