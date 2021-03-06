package com.animation.flyschool

import android.content.res.Resources
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ImageView
import com.core.utilities.LDeviceUtil.Companion.getRandomNumber
import com.core.utilities.LStoreUtil.Companion.randomColor

/**
 * Utility class with simple utility functions
 * Created by avin on 09/01/17.
 */
object Utils {
    /**
     * @param dp : Dimension in dp
     * Calculates and returns the dimension value in pixels from dp
     */
    @JvmStatic
    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }

    /**
     * Checks and tell us whether the android phone is on version < LOLLIPOP or not
     */
    val isLowerThanLollipop: Boolean
        get() = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP

    @JvmStatic
    fun setHeart(imageView: ImageView) {
        val size = getRandomNumber(150) + 80
        imageView.layoutParams.height = size
        imageView.layoutParams.width = size
        imageView.requestLayout()

        val color = randomColor
        imageView.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }
}
