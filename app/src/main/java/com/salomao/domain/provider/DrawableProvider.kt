package com.salomao.domain.provider

import android.graphics.drawable.Drawable

interface DrawableProvider {
    fun getDrawable(drawableID: Int): Drawable?
}
