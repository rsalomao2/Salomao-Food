package com.salomao.presentation.placelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.salomao.R
import com.salomao.data.pojo.Place
import com.salomao.databinding.LayoutPlaceListItemBinding
import com.squareup.picasso.Picasso

class PlaceAdapter(
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
            loadThumbnail(place)

            binding.tvName.text = place.name
            binding.tvAddress.text = place.address.getFullAddress()
            binding.ivPriceRange.setColorFilter(getColor(place.priceRange))
            binding.ratinBar.rating = place.rate
            binding.container.setOnClickListener { onItemClick(place) }
        }

        private fun loadThumbnail(place: Place) {
            if (place.thumb.isNullOrEmpty()) {
                Picasso.get()
                    .load(R.drawable.ic_no_image)
                    .placeholder(R.drawable.ic_no_image)
                    .error(R.drawable.ic_no_image)
                    .into(binding.ivThumb)
            } else
                Picasso.get()
                    .load(place.thumb)
                    .placeholder(R.drawable.ic_no_image)
                    .error(R.drawable.ic_no_image)
                    .into(binding.ivThumb)
        }

        private fun getColor(priceRange: Int): Int {
            return when (priceRange) {
                4 -> ContextCompat.getColor(binding.root.context, R.color.colorPriceRed)
                3 -> ContextCompat.getColor(binding.root.context, R.color.colorPriceOrange)
                2 -> ContextCompat.getColor(binding.root.context, R.color.colorPriceYellow)
                1 -> ContextCompat.getColor(binding.root.context, R.color.colorPriceGreen)
                else -> ContextCompat.getColor(binding.root.context, R.color.colorPriceRed)
            }
        }
    }
}
