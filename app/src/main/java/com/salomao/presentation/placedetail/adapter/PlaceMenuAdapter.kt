package com.salomao.presentation.placedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.salomao.R
import com.salomao.data.pojo.Dish
import com.salomao.databinding.LayoutMenuListItemBinding

class PlaceMenuAdapter(
    private val onItemClick: (Dish) -> (Unit)
) : RecyclerView.Adapter<PlaceMenuAdapter.MyViewHolder>() {


    private var items = ArrayList<Dish>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutMenuListItemBinding>(
            inflater,
            R.layout.layout_menu_list_item,
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

    fun addItems(itemsList: List<Dish>) {
        this.items.clear()
        this.items.addAll(itemsList)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: LayoutMenuListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dish: Dish) {
            binding.tvName.text = dish.name
            binding.tvPrice.text = dish.price
            binding.container.setOnClickListener { onItemClick(dish) }
        }
    }
}
