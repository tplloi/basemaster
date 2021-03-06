package com.views.textview.countdown

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.R
import com.core.utilities.LAnimationUtil
import com.daimajia.androidanimations.library.Techniques
import kotlinx.android.synthetic.main.view_l_count_down.view.*

class LCountDownView : RelativeLayout {

    interface Callback {
        fun onTick()
        fun onEnd()
    }

    private val logTag = javaClass.simpleName
    private var number = 0
    private var callback: Callback? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.view_l_count_down, this)
    }

    fun start(number: Int) {
        this.number = number
        doPerSec()
    }

    fun setShowOrHide(isShow: Boolean) {
        tvCountDown.visibility = if (isShow) {
            VISIBLE
        } else {
            INVISIBLE
        }
    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    private fun doPerSec() {
        tvCountDown.text = number.toString()
        LAnimationUtil.play(
                view = tvCountDown,
                techniques = Techniques.FlipInX,
                duration = 1000,
                onEnd = {
                    number--
                    if (number <= 0) {
                        tvCountDown.text = "GO"
                        LAnimationUtil.play(
                                view = tvCountDown,
                                techniques = Techniques.Flash,
                                onEnd = {
                                    tvCountDown?.visibility = GONE
                                    callback?.onEnd()
                                }
                        )
                    } else {
                        doPerSec()
                    }
                }
        )
    }
}
