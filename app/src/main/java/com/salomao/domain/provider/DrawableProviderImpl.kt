package com.salomao.domain.provider

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class DrawableProviderImpl(private val context: Context) : DrawableProvider {

    override fun getDrawable(drawableID: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableID)
    }
}
