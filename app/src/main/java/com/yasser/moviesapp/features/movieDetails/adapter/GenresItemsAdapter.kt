package com.yasser.moviesapp.features.movieDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yasser.data.genres.model.GenreModel
import com.yasser.moviesapp.databinding.ViewMovieDetailsGenresItemBinding


class GenresItemsAdapter(
    private val clickListener: (position: Int, item: GenreModel?) -> Unit
) : ListAdapter<GenreModel, GenresItemsAdapter.GenreItemViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreItemViewHolder {
        return GenreItemViewHolder.from(parent, clickListener)
    }


    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    

    class GenreItemViewHolder(
        private val binding: ViewMovieDetailsGenresItemBinding,
        private val clickListener: (position: Int, item: GenreModel?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GenreModel?) {
            binding.tvGenre.text = item?.name
        }

        companion object {
            fun from(
                parent: ViewGroup,
                clickListener: (position: Int, item: GenreModel?) -> Unit
            ): GenreItemViewHolder {
                return GenreItemViewHolder(
                    ViewMovieDetailsGenresItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    clickListener
                )
            }
        }

    }


    companion object DiffUtilCallback : DiffUtil.ItemCallback<GenreModel>() {
        override fun areItemsTheSame(
            oldItem: GenreModel,
            newItem: GenreModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GenreModel,
            newItem: GenreModel
        ): Boolean {
            return oldItem == newItem
        }

    }

}


