package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview01

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_tablayout_demo_custom_view01.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoColorPagerAdapter
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.utils.DemoData
import java.util.*

@LogTag("RvTabCustomView01Activity")
@IsFullScreen(false)
class RvTabCustomView01Activity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_demo_custom_view01
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val keyDemo = intent.getStringExtra(KEY_DEMO)
        if (keyDemo.isNullOrEmpty()) {
            onBackPressed()
            return
        }
        val demo = Demo.valueOf(keyDemo)

        toolbar.setTitle(demo.titleResId)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items = DemoData.loadDemoColorItems(this)
        items.sortedWith { lhs, rhs -> lhs.name.compareTo(rhs.name) }

        val demoColorPagerAdapter = DemoColorPagerAdapter()
        demoColorPagerAdapter.addAll(items)

        viewPager.adapter = demoColorPagerAdapter
        recyclerTabLayout.setUpWithAdapter(RvTabCustomView01Adapter(viewPager))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val KEY_DEMO = "demo"

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabCustomView01Activity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}