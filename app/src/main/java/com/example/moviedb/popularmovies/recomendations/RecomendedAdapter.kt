package com.example.moviedb.popularmovies.recomendations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.databinding.GenreLayoutBinding
import com.example.moviedb.databinding.RecomendedMovieLayoutBinding
import com.example.moviedb.model.casts.Cast
import com.example.moviedb.model.detail.Genre
import com.example.moviedb.model.recomendations.RecomendationResponse
import com.example.moviedb.model.recomendations.Recomendations
import com.example.moviedb.model.similar.Result
import com.example.moviedb.popularmovies.details.GenreAdapter
import com.example.moviedb.utils.DiffUtils

class RecomendedAdapter(private val clickListener: OnItemClickListener): RecyclerView.Adapter<RecomendedAdapter.ViewHolder>() {
    private var recomendedList = ArrayList<Recomendations>()

    class ViewHolder(val binding: RecomendedMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RecomendedAdapter.ViewHolder(
            RecomendedMovieLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500" + recomendedList[position].posterPath)
            .into(holder.binding.ivProfilepath)
        holder.binding.tvName.text = recomendedList[position].title
        holder.binding.tvCastingName.text = recomendedList[position].originalTitle

        holder.itemView.setOnClickListener {
            clickListener.onItemRecomendedClick(recomendedList[position])
        }

    }

    override fun getItemCount(): Int {
        return recomendedList.size

    }


    fun setData(newData:  List<Recomendations>){
        val recomendedDiffUtil =
            DiffUtils(recomendedList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recomendedDiffUtil)
        recomendedList = newData as ArrayList<Recomendations>
        diffUtilResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemRecomendedClick(recomendedMovies : Recomendations)
    }
}