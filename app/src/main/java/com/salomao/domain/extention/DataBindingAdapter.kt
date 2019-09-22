package com.salomao.domain.extention

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.salomao.R
import com.squareup.picasso.Picasso

@BindingAdapter("app:showView")
fun setVisibility(view: View, show: Boolean?) {
    view.visibility = if (show != null && show) View.VISIBLE else View.GONE
}

@BindingAdapter("app:loadImage")
fun setImageFromSource(view: ImageView, source: String?) {
    if(!source.isNullOrEmpty()){
        Picasso.get()
            .load(source)
            .placeholder(R.drawable.ic_no_image)
            .into(view)
    }

}

@BindingAdapter("app:loadPriceRangeImage")
fun setImageFromRange(view: ImageView, range: Int?) {
    view.setImageDrawable(view.context.getDrawable(getSource(range)))
}

private fun getSource(range: Int?): Int {
    return when (range) {
        1 -> R.drawable.ic_no_image
        2 -> R.drawable.ic_no_image
        3 -> R.drawable.ic_no_image
        4 -> R.drawable.ic_no_image
        else -> R.drawable.ic_no_image
    }
}
