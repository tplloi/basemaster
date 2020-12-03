package com.game.findnumber

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.game.findnumber.adapter.LevelAdapter
import com.game.findnumber.model.Level
import kotlinx.android.synthetic.main.l_activity_find_number_single_level.*

@LogTag("SingleLevelActivity")
@IsFullScreen(true)
class SingleLevelActivity : BaseFontActivity() {

    private var levelAdapter = LevelAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_single_level
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupViews()
        setupData()
    }

    private fun setupViews() {
        levelAdapter.onClickRootView = { level, view ->
            LUIUtil.setOnClickListenerElastic(
                    view = view,
                    runnable = {
                        //TODO loitpp iplm
                    })
        }
        rvLevel.layoutManager = GridLayoutManager(this, 4)
        rvLevel.adapter = levelAdapter

        LUIUtil.setDelay(100) {
            tvLevels?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = tvLevels,
                    duration = 500,
                    techniques = Techniques.ZoomInDown
            )

            ivBack?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = ivBack,
                    duration = 500,
                    techniques = Techniques.ZoomInUp
            )
        }
        LUIUtil.setSafeOnClickListenerElastic(
                view = tvLevels
        )
        LUIUtil.setSafeOnClickListenerElastic(
                view = ivBack,
                runnable = {
                    onBackPressed()
                })
    }

    private fun setupData() {
        val listLevel = ArrayList<Level>()
        for (i in 0 until 100) {
            val level = Level()
            level.name = "${i + 1}"
            listLevel.add(element = level)
        }
        levelAdapter.setListLevel(listLevel = listLevel)
    }
}
