package com.views.edittext.autoresize

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.R
import java.util.*

/**
 *
 * A [ViewGroup] that re-sizes the text of it's children to be no larger than the width of the
 * view.
 *
 * @attr ref R.styleable.AutofitTextView_sizeToFit
 * @attr ref R.styleable.AutofitTextView_minTextSize
 * @attr ref R.styleable.AutofitTextView_precision
 */
class AutofitLayout : FrameLayout {
    private var mEnabled = false
    private var mMinTextSize = 0f
    private var mPrecision = 0f
    private val mHelpers = WeakHashMap<View, AutofitHelper>()

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        var sizeToFit = true
        var minTextSize = -1
        var precision = -1f
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.AutofitEditText,
                    defStyle,
                    0
            )
            sizeToFit = typedArray.getBoolean(R.styleable.AutofitEditText_sizeToFit, sizeToFit)
            minTextSize = typedArray.getDimensionPixelSize(R.styleable.AutofitEditText_minTextSize, minTextSize)
            precision = typedArray.getFloat(R.styleable.AutofitEditText_precision, precision)
            typedArray.recycle()
        }
        mEnabled = sizeToFit
        mMinTextSize = minTextSize.toFloat()
        mPrecision = precision
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        val textView = child as TextView
        val helper = AutofitHelper.create(textView).setEnabled(mEnabled)
        if (mPrecision > 0) {
            helper.precision = mPrecision
        }
        if (mMinTextSize > 0) {
            helper.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, mMinTextSize)
        }
        mHelpers[textView] = helper
    }

    /**
     * Returns the [AutofitHelper] for this child View.
     */
    fun getAutofitHelper(textView: TextView): AutofitHelper? {
        return mHelpers[textView]
    }

    /**
     * Returns the [AutofitHelper] for this child View.
     */
    fun getAutofitHelper(index: Int): AutofitHelper? {
        return mHelpers[getChildAt(index)]
    }
}
