package com.example.moviedb.popularmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.databinding.MovieLayoutBinding
import com.example.moviedb.model.movies.Result
import com.example.moviedb.utils.DiffUtils

class MovieAdapter(private val clickListener: OnItemClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var movieList = ArrayList<Result>()




    class ViewHolder(val binding: MovieLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500" + movieList[position].poster_path)
            .into(holder.binding.movieImage)

        holder.binding.movieName.text = movieList[position].title

        holder.itemView.setOnClickListener {
            clickListener.onItemEditCLick(movieList[position])
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovieList(movieList: List<Result>) {
        this.movieList = movieList as ArrayList<Result>
        notifyDataSetChanged()
    }

    fun setData(newData:  List<Result>){
        val moviesDiffUtil =
            DiffUtils(movieList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(moviesDiffUtil)
        movieList = newData as ArrayList<Result>
        diffUtilResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemEditCLick(movies : Result)
    }
}