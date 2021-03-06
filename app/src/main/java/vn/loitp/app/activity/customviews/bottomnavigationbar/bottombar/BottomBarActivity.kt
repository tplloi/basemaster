package vn.loitp.app.activity.customviews.bottomnavigationbar.bottombar

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAppResource
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.views.bottombar.LBottomBar
import kotlinx.android.synthetic.main.activity_bottom_bar_blur.*
import vn.loitp.app.R

@LogTag("BottomBarActivity")
@IsFullScreen(false)
class BottomBarActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_bottom_bar_blur
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.loitp)
        with(bottomBar) {
            paddingOnInDp = context.resources.getDimension(R.dimen.padding_10).toInt()
            paddingOffInDp = context.resources.getDimension(R.dimen.padding_15).toInt()
            colorIvOn = R.color.red
            colorIvOff = R.color.pink
            setTextMarginBottom(context.resources.getDimension(R.dimen.margin_5).toInt())
            setItem0(R.drawable.ic_bug_report_black_48dp, "Bug report")
            setItem1(R.drawable.ic_add_black_48dp, "Add")
            setItem2(R.drawable.ic_chat_black_48dp, "Chat")
            setItem3(R.drawable.ic_close_black_48dp, "Clear")
            setItem4(R.drawable.ic_cloud_download_black_48dp, "Cloud")
            setItem5(R.drawable.ic_picture_in_picture_alt_black_48dp, "Picture")
            setTechniques(Techniques.Pulse)
            setOnItemClick(object : LBottomBar.Callback {
                override fun onClickItem(position: Int) {
                    showShortInformation("Touch $position")
                }
            })
        }
        btBlurViewRed.setOnClickListener {
            bottomBar.getRealTimeBlurView().setOverlayColor(LAppResource.getColor(R.color.red50))
        }
        btBlurViewGreen.setOnClickListener {
            bottomBar.getRealTimeBlurView().setOverlayColor(LAppResource.getColor(R.color.green33))
        }
        btCount1.setOnClickListener {
            bottomBar.setCount(1)
        }
        btCount3.setOnClickListener {
            bottomBar.setCount(3)
        }
        btCount5.setOnClickListener {
            bottomBar.setCount(5)
        }
        btCount6.setOnClickListener {
            bottomBar.setCount(6)
        }
        btShowText.setOnClickListener {
            bottomBar.isAlwayShowText = true
        }
        btHideText.setOnClickListener {
            bottomBar.isAlwayShowText = false
        }
        LUIUtil.setDelay(5000) {
            bottomBar?.setPerformItemClick(4)
        }
    }

}
