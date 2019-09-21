package com.salomao.domain.extention

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

private var mToast: Toast? = null

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    mToast?.cancel()
    mToast = Toast.makeText(context, message, duration)
    mToast?.show()
}

fun Fragment.hideKeyboard() {
    try {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (activity?.currentFocus != null) {
            inputManager.hideSoftInputFromWindow(
                activity!!.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    } catch (ignored: NullPointerException) {
    }

}
