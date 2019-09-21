package com.salomao.presentation.placelist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.salomao.R
import com.salomao.data.pojo.Place
import com.salomao.databinding.LayoutPlaceListItemBinding
import com.salomao.domain.provider.DrawableProvider
import com.squareup.picasso.Picasso

class PlaceAdapter(
    private val drawableProvider: DrawableProvider,
    private val onItemClick: (Place) -> (Unit)
) : RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {


    private var items = ArrayList<Place>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutPlaceListItemBinding>(
            inflater,
            R.layout.layout_place_list_item,
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(itemsList: List<Place>) {
        this.items.clear()
        this.items.addAll(itemsList)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: LayoutPlaceListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            Picasso.get()
                .load(place.thumb)
                .placeholder(R.drawable.ic_no_image)
                .into(binding.ivThumb)

            binding.tvName.text = place.name
            binding.tvAddress.text = place.address.getFullAddress()
            binding.ivPriceRange.setImageDrawable(drawableProvider.getDrawable(getDrawableId(place.priceRange)))
            binding.ratinBar.rating = place.rate
            binding.container.setOnClickListener { onItemClick(place) }
        }

    }

    private fun getDrawableId(priceRange: Int): Int {
        return when(priceRange){
            1 -> R.drawable.ic_no_image
            2 -> R.drawable.ic_no_image
            3 -> R.drawable.ic_no_image
            4 -> R.drawable.ic_no_image
            else -> R.drawable.ic_no_image
        }
    }
}
