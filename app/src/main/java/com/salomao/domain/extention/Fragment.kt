package com.salomao.domain.extention

import android.widget.Toast
import androidx.fragment.app.Fragment

private var mToast: Toast? = null

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    mToast?.cancel()
    mToast = Toast.makeText(context, message, duration)
    mToast?.show()
}
