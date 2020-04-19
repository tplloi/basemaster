package vn.loitp.app.activity.customviews.button.goodview

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.views.button.goodview.LGoodView
import kotlinx.android.synthetic.main.activity_good_view.*
import vn.loitp.app.R

//https://github.com/venshine/GoodView
class GoodViewActivity : BaseFontActivity() {
    private var lGoodView: LGoodView? = null

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_good_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lGoodView = LGoodView(activity)
        bt.setOnClickListener { v: View? ->
            lGoodView?.let {
                it.setText("+1")
                it.show(v)
            }
        }
        iv.setOnClickListener { v: View? ->
            iv.setColorFilter(Color.TRANSPARENT)
            lGoodView?.let {
                it.setImage(R.mipmap.ic_launcher)
                //it.setDistance(1000)
                //it.setTranslateY(0, 10000)
                //it.setAlpha(0, 0.5f)
                //it.setDuration(3000)
                it.show(v)
            }
        }
    }
}