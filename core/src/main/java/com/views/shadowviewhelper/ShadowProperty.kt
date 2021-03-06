package com.views.shadowviewhelper

import java.io.Serializable

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/25/15.
 */
class ShadowProperty : Serializable {

    /**
     * 阴影颜色
     */
    private var shadowColor: Int = 0
    /**
     * 阴影半径
     */
    private var shadowRadius: Int = 0
    /**
     * 阴影x偏移
     */
    private var shadowDx: Int = 0
    /**
     * 阴影y偏移
     */
    private var shadowDy: Int = 0

    /**
     * 阴影边
     */
    private var shadowSide = ALL

    val shadowOffset: Int
        get() = shadowOffsetHalf * 2

    val shadowOffsetHalf: Int
        get() = if (0 >= shadowRadius) 0 else Math.max(shadowDx, shadowDy) + shadowRadius

    fun getShadowSide(): Int {
        return shadowSide
    }

    fun setShadowSide(shadowSide: Int): ShadowProperty {
        this.shadowSide = shadowSide
        return this
    }

    fun getShadowColor(): Int {
        return shadowColor
    }

    fun setShadowColor(shadowColor: Int): ShadowProperty {
        this.shadowColor = shadowColor
        return this
    }

    fun getShadowRadius(): Int {
        return shadowRadius
    }

    fun setShadowRadius(shadowRadius: Int): ShadowProperty {
        this.shadowRadius = shadowRadius
        return this
    }

    fun getShadowDx(): Int {
        return shadowDx
    }

    fun setShadowDx(shadowDx: Int): ShadowProperty {
        this.shadowDx = shadowDx
        return this
    }

    fun getShadowDy(): Int {
        return shadowDy
    }

    fun setShadowDy(shadowDy: Int): ShadowProperty {
        this.shadowDy = shadowDy
        return this
    }

    companion object {
        val ALL = 0x1111
        val LEFT = 0x0001
        val TOP = 0x0010
        val RIGHT = 0x0100
        val BOTTOM = 0x1000
    }
}
