package vn.loitp.app.activity.customviews.layout.circularview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.layout.circularview.CircularView
import com.views.layout.circularview.Marker
import kotlinx.android.synthetic.main.activity_layout_circular_view.*
import vn.loitp.app.R

//https://github.com/sababado/CircularView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=238

@LogTag("CircularViewActivity")
@IsFullScreen(false)
class CircularViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_circular_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val simpleCircularViewAdapter = SimpleCircularViewAdapter()
        circularView.adapter = simpleCircularViewAdapter
        circularView.setOnCircularViewObjectClickListener(object : CircularView.OnClickListener {
            override fun onClick(view: CircularView, isLongClick: Boolean) {
                showShortInformation("onClick")
            }

            override fun onMarkerClick(view: CircularView, marker: Marker, position: Int, isLongClick: Boolean) {
                showShortInformation("onClick $position")
            }
        })
    }

}
