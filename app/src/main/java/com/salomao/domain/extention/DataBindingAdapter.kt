package com.salomao.domain.extention

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
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

@BindingAdapter("app:colorByPriceRange")
fun setColorFromRange(view: ImageView, range: Int?) {
    view.setColorFilter(getColor(view.context, range))
}

private fun getColor(context: Context, priceRange: Int?): Int {
    return when (priceRange) {
        4 -> ContextCompat.getColor(context, R.color.colorPriceRed)
        3 -> ContextCompat.getColor(context, R.color.colorPriceOrange)
        2 -> ContextCompat.getColor(context, R.color.colorPriceYellow)
        1 -> ContextCompat.getColor(context, R.color.colorPriceGreen)
        else -> ContextCompat.getColor(context, R.color.colorPriceRed)
    }
}
