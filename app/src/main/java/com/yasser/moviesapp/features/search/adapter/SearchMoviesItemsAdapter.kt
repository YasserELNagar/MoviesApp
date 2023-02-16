package com.yasser.moviesapp.features.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yasser.data.movies.model.MovieModel
import com.yasser.moviesapp.core.ui.loadImageByUrl
import com.yasser.moviesapp.databinding.ViewMoviesSearchItemBinding


class SearchMoviesItemsAdapter(
    private val clickListener: (position: Int, item: MovieModel?) -> Unit
) : ListAdapter<MovieModel, SearchMoviesItemsAdapter.SearchMovieItemViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMovieItemViewHolder {
        return SearchMovieItemViewHolder.from(parent, clickListener)
    }


    override fun onBindViewHolder(holder: SearchMovieItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    

    class SearchMovieItemViewHolder(
        private val binding: ViewMoviesSearchItemBinding,
        private val clickListener: (position: Int, item: MovieModel?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieModel?) {
            binding.tvRating.text = item?.vote_average.toString()
            binding.tvName.text = item?.original_title
            binding.tvDate.text = item?.release_date
            item?.poster_path?.let {
                binding.ivBooster.loadImageByUrl(it)
            }

            binding.root.setOnClickListener {
                clickListener(adapterPosition, item)
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                clickListener: (position: Int, item: MovieModel?) -> Unit
            ): SearchMovieItemViewHolder {
                return SearchMovieItemViewHolder(
                    ViewMoviesSearchItemBinding.inflate(
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


