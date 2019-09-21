package com.salomao.provider

import android.content.Context
import com.salomao.provider.StringProvider

class StringProviderImpl(private val context: Context): StringProvider {
    override fun getString(stringID: Int): String {
        return context.getString(stringID)
    }
}
