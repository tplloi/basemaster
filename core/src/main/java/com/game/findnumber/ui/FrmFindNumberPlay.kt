package com.game.findnumber.ui

import android.app.ActivityOptions
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.R
import com.animation.morphtransitions.MorphTransform
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFragment
import com.core.utilities.LAnimationUtil
import com.core.utilities.LAppResource
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.game.findnumber.dialog.FindNumberWinActivity
import com.game.findnumber.model.Level
import kotlinx.android.synthetic.main.l_frm_find_number_play.*

@LogTag("loitppFrmFindNumberPlay")
class FrmFindNumberPlay(
        val level: Level
) : BaseFragment() {

    private var numberTarget = 1
    private var mRows = 2
    private var mCols = 2
    private val listData = ArrayList<String>()

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_find_number_play
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupDataLevel()
        setupData()
    }

    private fun setupViews() {
        setupNumberTarget()
    }

    private fun setupNumberTarget() {
        tvNumberTarget.text = "$numberTarget"
        LAnimationUtil.play(view = tvNumberTarget, techniques = Techniques.Flash)
    }

    private fun setupDataLevel() {
        logD("setupDataLevel " + BaseApplication.gson.toJson(level))
        when {
            level.name < 10 -> {
                mRows = 2
                mCols = 2
            }
            level.name < 20 -> {
                mRows = 3
                mCols = 3
            }
            level.name < 30 -> {
                mRows = 4
                mCols = 4
            }
            level.name < 40 -> {
                mRows = 5
                mCols = 5
            }
            level.name < 50 -> {
                mRows = 6
                mCols = 6
            }
            level.name < 60 -> {
                mRows = 7
                mCols = 7
            }
            level.name < 70 -> {
                mRows = 8
                mCols = 8
            }
            level.name < 80 -> {
                mRows = 9
                mCols = 9
            }
            level.name < 90 -> {
                mRows = 10
                mCols = 10
            }
            else -> {
                mRows = 2
                mCols = 2
            }
        }
    }

    private fun setupData() {
        val size = mRows * mCols
        for (i in 0..size) {
            listData.add("${i + 1}")
        }
        listData.shuffle()

        val color1 = LAppResource.getColor(R.color.green)
        val color2 = LAppResource.getColor(R.color.orange)
        var layoutParams: ConstraintLayout.LayoutParams
        var id: Int
        val idArray = Array(mRows) {
            IntArray(mCols)
        }
        val constraintSet = ConstraintSet()

        var index = 0
        for (iRow in 0 until mRows) {
            for (iCol in 0 until mCols) {
                val textView = TextView(context)
                layoutParams = ConstraintLayout.LayoutParams(ConstraintSet.MATCH_CONSTRAINT, ConstraintSet.MATCH_CONSTRAINT)
                id = View.generateViewId()
                idArray[iRow][iCol] = id
                textView.id = id
                textView.text = listData[index]
                index++
//                textView.rotation = 45f
                textView.gravity = Gravity.CENTER
                textView.setTextColor(Color.WHITE)
                textView.setBackgroundColor(if ((iRow + iCol) % 2 == 0) color1 else color2)

                LUIUtil.setSafeOnClickListenerElastic(
                        view = textView,
                        runnable = {
                            showShortInformation("${textView.text}")
                        })
                layoutRootView.addView(textView, layoutParams)

            }
        }

        constraintSet.clone(layoutRootView)
        val gridFrameId = R.id.gridFrame
        constraintSet.setDimensionRatio(gridFrameId, "$mCols:$mRows")
        for (iRow in 0 until mRows) {
            for (iCol in 0 until mCols) {
                id = idArray[iRow][iCol]
                constraintSet.setDimensionRatio(id, "1:1")
                if (iRow == 0) {
                    constraintSet.connect(id, ConstraintSet.TOP, gridFrameId, ConstraintSet.TOP)
                } else {
                    constraintSet.connect(id, ConstraintSet.TOP, idArray[iRow - 1][0], ConstraintSet.BOTTOM)
                }
            }
            constraintSet.createHorizontalChain(
                    gridFrameId, ConstraintSet.LEFT,
                    gridFrameId, ConstraintSet.RIGHT,
                    idArray[iRow], null, ConstraintSet.CHAIN_PACKED
            )
        }
        constraintSet.applyTo(layoutRootView)
    }

    private fun winGame(view: View) {
        activity?.let { a ->
            val intent = FindNumberWinActivity.newIntent(a, FindNumberWinActivity.TYPE_BUTTON)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                MorphTransform.addExtras(
                        intent,
                        ContextCompat.getColor(a, R.color.colorAccent),
                        resources.getDimensionPixelSize(R.dimen.round_medium)
                )
                val options = ActivityOptions.makeSceneTransitionAnimation(
                        a,
                        view,
                        getString(R.string.transition_morph)
                )
                startActivity(intent, options.toBundle())
            } else {
                startActivity(intent)
                a.overridePendingTransition(R.anim.anim_morph_transitions_fade_in, R.anim.anim_morph_transitions_do_nothing)
            }
        }
    }
}
