package vn.loitp.app.activity.animation.elasticviews

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.skydoves.elasticviews.ElasticFinishListener
import com.skydoves.elasticviews.elasticAnimation
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_elastic_view.*
import vn.loitp.app.R

@LogTag("MenuAnimationActivity")
@IsFullScreen(false)
class ElasticActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_elastic_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        //dont remove this code below
        elasticButton.setSafeOnClickListener {
        }
        elasticCheckButton.setSafeOnClickListener {
        }
        elasticImageView.setSafeOnClickListener {
        }
        elasticFloatingActionButton.setSafeOnClickListener {
        }
        elasticCardView.setSafeOnClickListener {
        }
        LUIUtil.setOnClickListenerElastic(
                view = anyView,
                runnable = {
                    showShortInformation("Finish setOnClickListenerElastic")
                })
        LUIUtil.setSafeOnClickListenerElastic(
                view = anyView2,
                runnable = {
                    showShortInformation("Finish setSafeOnClickListenerElastic")
                })

    }

}
