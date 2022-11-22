package com.example.moviedb.popularmovies.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.databinding.GenreLayoutBinding
import com.example.moviedb.model.detail.Genre
import com.example.moviedb.utils.DiffUtils

class GenreAdapter: RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private var genreList = ArrayList<Genre>()

    class ViewHolder(val binding: GenreLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return GenreAdapter.ViewHolder(
            GenreLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvGenre.text = genreList[position].name
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    fun setData(newData:  List<Genre>){
        val genreDiffUtil =
            DiffUtils(genreList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(genreDiffUtil)
        genreList = newData as ArrayList<Genre>
        diffUtilResult.dispatchUpdatesTo(this)
    }
}