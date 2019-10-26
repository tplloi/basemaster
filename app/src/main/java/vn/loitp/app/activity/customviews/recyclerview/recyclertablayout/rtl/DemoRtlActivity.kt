package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.rtl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.views.recyclerview.recyclertablayout.RecyclerTabLayout
import loitp.basemaster.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoColorPagerAdapter
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.basic.DemoBasicActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.utils.DemoData
import java.util.*

class DemoRtlActivity : BaseFontActivity() {

    protected lateinit var mRecyclerTabLayout: RecyclerTabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val demo = Demo.valueOf(intent.getStringExtra(DemoBasicActivity.KEY_DEMO))

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(demo.titleResId)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items = DemoData.loadDemoColorItems(this)
        items.sortedWith(Comparator { lhs, rhs -> lhs.name!!.compareTo(rhs.name!!) })

        val adapter = DemoColorPagerAdapter()
        adapter.addAll(items)

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = adapter

        mRecyclerTabLayout = findViewById(R.id.recycler_tab_layout)
        mRecyclerTabLayout.setUpWithViewPager(viewPager)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_demo_rtl
    }

    companion object {

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, DemoRtlActivity::class.java)
            intent.putExtra(DemoBasicActivity.KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}