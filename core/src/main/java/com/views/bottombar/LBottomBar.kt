package com.views.bottombar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.R
import com.core.utilities.LAnimationUtil
import com.core.utilities.LAppResource
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.github.mmin18.widget.RealtimeBlurView
import kotlinx.android.synthetic.main.view_l_bottom_bar.view.*

/**
 * Created by www.muathu@gmail.com on 7/25/2019.
 */

class LBottomBar : RelativeLayout, View.OnClickListener {

    companion object {
        const val PAGE_NONE = -1
        const val PAGE_0 = 0
        const val PAGE_1 = 1
        const val PAGE_2 = 2
        const val PAGE_3 = 3
        const val PAGE_4 = 4
        const val PAGE_5 = 5
    }

    private val logTag = javaClass.simpleName
    private var previousPos: Int = 0
    private var currentPos: Int = 0
    var isAlwayShowText = true
        set(value) {
            field = value
            refreshUI()
        }
    var colorIvOn = R.color.colorPrimary
        set(colorOn) {
            field = colorOn
            refreshUI()
        }
    var colorIvOff = R.color.black
        set(colorOff) {
            field = colorOff
            refreshUI()
        }
    private var techniques: Techniques? = Techniques.Pulse

    var paddingOnInDp = 5
    var paddingOffInDp = 25

    private var callback: Callback? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_l_bottom_bar, this)

        LUIUtil.setRipple(view = layoutIcon0)
        LUIUtil.setRipple(view = layoutIcon1)
        LUIUtil.setRipple(view = layoutIcon2)
        LUIUtil.setRipple(view = layoutIcon3)
        LUIUtil.setRipple(view = layoutIcon4)
        LUIUtil.setRipple(view = layoutIicon5)

        layoutIcon0.setOnClickListener(this)
        layoutIcon1.setOnClickListener(this)
        layoutIcon2.setOnClickListener(this)
        layoutIcon3.setOnClickListener(this)
        layoutIcon4.setOnClickListener(this)
        layoutIicon5.setOnClickListener(this)

        updateView(imageView = ivIcon0, textView = tvIcon0)
    }

    fun setCount(count: Int) {
        if (count !in 0 until 7) {
            throw IllegalArgumentException("Error value of count number, value must between 0 and 7.")
        }
        when (count) {
            0 -> {
                layoutIcon0.visibility = View.GONE
                layoutIcon1.visibility = View.GONE
                layoutIcon2.visibility = View.GONE
                layoutIcon3.visibility = View.GONE
                layoutIcon4.visibility = View.GONE
                layoutIicon5.visibility = View.GONE
            }
            1 -> {
                layoutIcon0.visibility = View.VISIBLE
                layoutIcon1.visibility = View.GONE
                layoutIcon2.visibility = View.GONE
                layoutIcon3.visibility = View.GONE
                layoutIcon4.visibility = View.GONE
                layoutIicon5.visibility = View.GONE
            }
            2 -> {
                layoutIcon0.visibility = View.VISIBLE
                layoutIcon1.visibility = View.VISIBLE
                layoutIcon2.visibility = View.GONE
                layoutIcon3.visibility = View.GONE
                layoutIcon4.visibility = View.GONE
                layoutIicon5.visibility = View.GONE
            }
            3 -> {
                layoutIcon0.visibility = View.VISIBLE
                layoutIcon1.visibility = View.VISIBLE
                layoutIcon2.visibility = View.VISIBLE
                layoutIcon3.visibility = View.GONE
                layoutIcon4.visibility = View.GONE
                layoutIicon5.visibility = View.GONE
            }
            4 -> {
                layoutIcon0.visibility = View.VISIBLE
                layoutIcon1.visibility = View.VISIBLE
                layoutIcon2.visibility = View.VISIBLE
                layoutIcon3.visibility = View.VISIBLE
                layoutIcon4.visibility = View.GONE
                layoutIicon5.visibility = View.GONE
            }
            5 -> {
                layoutIcon0.visibility = View.VISIBLE
                layoutIcon1.visibility = View.VISIBLE
                layoutIcon2.visibility = View.VISIBLE
                layoutIcon3.visibility = View.VISIBLE
                layoutIcon4.visibility = View.VISIBLE
                layoutIicon5.visibility = View.GONE
            }
            6 -> {
                layoutIcon0.visibility = View.VISIBLE
                layoutIcon1.visibility = View.VISIBLE
                layoutIcon2.visibility = View.VISIBLE
                layoutIcon3.visibility = View.VISIBLE
                layoutIcon4.visibility = View.VISIBLE
                layoutIicon5.visibility = View.VISIBLE
            }
        }
    }

    fun setItem0(resImg: Int, name: String) {
        ivIcon0.setImageResource(resImg)
        tvIcon0.text = name
    }

    fun setItem1(resImg: Int, name: String) {
        ivIcon1.setImageResource(resImg)
        tvIcon1.text = name
    }

    fun setItem2(resImg: Int, name: String) {
        ivIcon2.setImageResource(resImg)
        tvIcon2.text = name
    }

    fun setItem3(resImg: Int, name: String) {
        ivIcon3.setImageResource(resImg)
        tvIcon3.text = name
    }

    fun setItem4(resImg: Int, name: String) {
        ivIcon4.setImageResource(resImg)
        tvIcon4.text = name
    }

    fun setItem5(resImg: Int, name: String) {
        ivIcon5.setImageResource(resImg)
        tvIcon5.text = name
    }

    override fun onClick(v: View) {
        if (v === layoutIcon0) {
            if (currentPos != PAGE_0) {
                previousPos = currentPos
                currentPos = PAGE_0
                onClickItem(currentPos)
                updateView(imageView = ivIcon0, textView = tvIcon0)
            }
        } else if (v === layoutIcon1) {
            if (currentPos != PAGE_1) {
                previousPos = currentPos
                currentPos = PAGE_1
                onClickItem(currentPos)
                updateView(imageView = ivIcon1, textView = tvIcon1)
            }
        } else if (v === layoutIcon2) {
            if (currentPos != PAGE_2) {
                previousPos = currentPos
                currentPos = PAGE_2
                onClickItem(currentPos)
                updateView(imageView = ivIcon2, textView = tvIcon2)
            }
        } else if (v === layoutIcon3) {
            if (currentPos != PAGE_3) {
                previousPos = currentPos
                currentPos = PAGE_3
                onClickItem(currentPos)
                updateView(imageView = ivIcon3, textView = tvIcon3)
            }
        } else if (v === layoutIcon4) {
            if (currentPos != PAGE_4) {
                previousPos = currentPos
                currentPos = PAGE_4
                onClickItem(currentPos)
                updateView(imageView = ivIcon4, textView = tvIcon4)
            }
        } else if (v === layoutIicon5) {
            if (currentPos != PAGE_5) {
                previousPos = currentPos
                currentPos = PAGE_5
                onClickItem(currentPos)
                updateView(imageView = ivIcon5, textView = tvIcon5)
            }
        }
    }

    fun setTechniques(techniques: Techniques) {
        this.techniques = techniques
    }

    private fun refreshUI() {
        when (currentPos) {
            PAGE_0 -> updateView(imageView = ivIcon0, textView = tvIcon0)
            PAGE_1 -> updateView(imageView = ivIcon1, textView = tvIcon1)
            PAGE_2 -> updateView(imageView = ivIcon2, textView = tvIcon2)
            PAGE_3 -> updateView(imageView = ivIcon3, textView = tvIcon3)
            PAGE_4 -> updateView(imageView = ivIcon4, textView = tvIcon4)
            PAGE_5 -> updateView(imageView = ivIcon5, textView = tvIcon5)
        }
    }

    private fun updateView(imageView: ImageView, textView: TextView) {
        techniques?.let {
            LAnimationUtil.play(view = imageView, techniques = it)
            LAnimationUtil.play(view = textView, techniques = it)
        }

        tvIcon0.setTextColor(LAppResource.getColor(this.colorIvOff))
        tvIcon1.setTextColor(LAppResource.getColor(this.colorIvOff))
        tvIcon2.setTextColor(LAppResource.getColor(this.colorIvOff))
        tvIcon3.setTextColor(LAppResource.getColor(this.colorIvOff))
        tvIcon4.setTextColor(LAppResource.getColor(this.colorIvOff))
        tvIcon5.setTextColor(LAppResource.getColor(this.colorIvOff))
        textView.setTextColor(LAppResource.getColor(this.colorIvOn))

        if (isAlwayShowText) {
            tvIcon0.visibility = View.VISIBLE
            tvIcon1.visibility = View.VISIBLE
            tvIcon2.visibility = View.VISIBLE
            tvIcon3.visibility = View.VISIBLE
            tvIcon4.visibility = View.VISIBLE
            tvIcon5.visibility = View.VISIBLE
        } else {
            tvIcon0.visibility = View.GONE
            tvIcon1.visibility = View.GONE
            tvIcon2.visibility = View.GONE
            tvIcon3.visibility = View.GONE
            tvIcon4.visibility = View.GONE
            tvIcon5.visibility = View.GONE
        }
        ivIcon0.let {
            it.setColorFilter(LAppResource.getColor(this.colorIvOff))
            it.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        }
        ivIcon1.let {
            it.setColorFilter(LAppResource.getColor(this.colorIvOff))
            it.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        }
        ivIcon2.let {
            it.setColorFilter(LAppResource.getColor(this.colorIvOff))
            it.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        }
        ivIcon3.let {
            it.setColorFilter(LAppResource.getColor(this.colorIvOff))
            it.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        }
        ivIcon4.let {
            it.setColorFilter(LAppResource.getColor(this.colorIvOff))
            it.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        }
        ivIcon5.let {
            it.setColorFilter(LAppResource.getColor(this.colorIvOff))
            it.setPadding(paddingOffInDp, paddingOffInDp, paddingOffInDp, paddingOffInDp)
        }

        imageView.let {
            it.setColorFilter(LAppResource.getColor(this.colorIvOn))
            it.setPadding(paddingOnInDp, paddingOnInDp, paddingOnInDp, paddingOnInDp)
        }
        textView.visibility = View.VISIBLE
    }

    private fun onClickItem(pos: Int) {
        callback?.onClickItem(pos)
    }

    interface Callback {
        fun onClickItem(position: Int)
    }

    fun setOnItemClick(callback: Callback) {
        this.callback = callback
    }

    fun setPerformItemClick(position: Int, invokedOnClickItem: Boolean = true) {
        previousPos = currentPos
        currentPos = position
        when (position) {
            PAGE_0 -> {
                if (invokedOnClickItem) {
                    onClickItem(PAGE_0)
                }
                updateView(ivIcon0, tvIcon0)
            }
            PAGE_1 -> {
                if (invokedOnClickItem) {
                    onClickItem(PAGE_1)
                }
                updateView(ivIcon1, tvIcon1)
            }
            PAGE_2 -> {
                if (invokedOnClickItem) {
                    onClickItem(PAGE_2)
                }
                updateView(ivIcon2, tvIcon2)
            }
            PAGE_3 -> {
                if (invokedOnClickItem) {
                    onClickItem(PAGE_3)
                }
                updateView(ivIcon3, tvIcon3)
            }
            PAGE_4 -> {
                if (invokedOnClickItem) {
                    onClickItem(PAGE_4)
                }
                updateView(ivIcon4, tvIcon4)
            }
            PAGE_5 -> {
                if (invokedOnClickItem) {
                    onClickItem(PAGE_5)
                }
                updateView(ivIcon5, tvIcon5)
            }
        }
    }

    fun setTextMarginBottom(bottomPx: Int) {
        LUIUtil.setMargins(view = tvIcon0, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = bottomPx)
        LUIUtil.setMargins(view = tvIcon1, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = bottomPx)
        LUIUtil.setMargins(view = tvIcon2, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = bottomPx)
        LUIUtil.setMargins(view = tvIcon3, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = bottomPx)
        LUIUtil.setMargins(view = tvIcon4, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = bottomPx)
        LUIUtil.setMargins(view = tvIcon5, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = bottomPx)
    }

    fun getCurrentPos(): Int {
        return currentPos
    }

    fun getRealTimeBlurView(): RealtimeBlurView {
        return realTimeBlurView
    }
}
