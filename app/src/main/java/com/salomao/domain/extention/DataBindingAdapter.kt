package com.salomao.domain.extention

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:showView")
fun setVisibility(view: View, show: Boolean?) {
    view.visibility = if (show != null && show) View.VISIBLE else View.GONE
}
