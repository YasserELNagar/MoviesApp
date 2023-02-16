package com.yasser.moviesapp.features.sortAndFilter.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yasser.moviesapp.R
import com.yasser.moviesapp.databinding.ViewSortAndFilterItemBinding


class SortAndFilterItemsAdapter(
    private val clickListener: (position: Int, item: SortAndFilterItem?) -> Unit
) : ListAdapter<SortAndFilterItem, SortAndFilterItemsAdapter.SortAndFilterItemViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SortAndFilterItemViewHolder {
        return SortAndFilterItemViewHolder.from(parent, clickListener)
    }


    override fun onBindViewHolder(holder: SortAndFilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    

    class SortAndFilterItemViewHolder(
        private val binding: ViewSortAndFilterItemBinding,
        private val clickListener: (position: Int, item: SortAndFilterItem?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: SortAndFilterItem?) {
            val context = binding.root.context
            binding.btnItem.text = item?.name

            if (item?.isSelected==true){
                binding.btnItem.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.colorRed))
                binding.btnItem.setTextColor(ColorStateList.valueOf(context.resources.getColor(R.color.white)))
            }
            else{
                binding.btnItem.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.white))
                binding.btnItem.setTextColor(ColorStateList.valueOf(context.resources.getColor(R.color.colorRed)))
            }

            binding.btnItem.setOnClickListener {
                clickListener(adapterPosition, item)
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                clickListener: (position: Int, item: SortAndFilterItem?) -> Unit
            ): SortAndFilterItemViewHolder {
                return SortAndFilterItemViewHolder(
                    ViewSortAndFilterItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    clickListener
                )
            }
        }

    }


    companion object DiffUtilCallback : DiffUtil.ItemCallback<SortAndFilterItem>() {
        override fun areItemsTheSame(
            oldItem: SortAndFilterItem,
            newItem: SortAndFilterItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SortAndFilterItem,
            newItem: SortAndFilterItem
        ): Boolean {
            return oldItem == newItem
        }

    }

}


