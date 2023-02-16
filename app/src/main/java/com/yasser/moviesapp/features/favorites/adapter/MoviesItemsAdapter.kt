package com.yasser.moviesapp.features.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yasser.data.movies.model.MovieModel
import com.yasser.moviesapp.R
import com.yasser.moviesapp.core.ui.loadImageByUrl
import com.yasser.moviesapp.databinding.ViewMoviesCardItemBinding


class MoviesItemsAdapter(
    private val clickListener: (position: Int, item: MovieModel?, actionType: MovieItemActionType) -> Unit
) : ListAdapter<MovieModel, MoviesItemsAdapter.MovieItemViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieItemViewHolder {
        return MovieItemViewHolder.from(parent, clickListener)
    }


    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    

    class MovieItemViewHolder(
        private val binding: ViewMoviesCardItemBinding,
        private val clickListener: (position: Int, item: MovieModel?, actionType: MovieItemActionType) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieModel?) {
            binding.tvRating.text = item?.vote_average.toString()
            binding.ivFavorite.setImageResource(
                if (item?.isFavorite == true)
                    R.drawable.ic_love_selected
                else
                    R.drawable.ic_love_unselected
            )
            item?.poster_path?.let {
                binding.ivBooster.loadImageByUrl(it)
            }

            binding.root.setOnClickListener {
                clickListener(adapterPosition, item, MovieItemActionType.VIEW_MOVIE)
            }
            binding.ivFavorite.setOnClickListener {
                clickListener(adapterPosition, item, MovieItemActionType.REMOVE_FAVORITE)
            }

        }

        companion object {
            fun from(
                parent: ViewGroup,
                clickListener: (position: Int, item: MovieModel?, actionType: MovieItemActionType) -> Unit
            ): MovieItemViewHolder {
                return MovieItemViewHolder(
                    ViewMoviesCardItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    clickListener
                )
            }
        }

    }


    companion object DiffUtilCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel
        ): Boolean {
            return oldItem == newItem
        }

    }

}


