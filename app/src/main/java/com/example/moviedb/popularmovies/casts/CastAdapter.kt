package com.example.moviedb.popularmovies.casts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.databinding.CastRowLayoutBinding
import com.example.moviedb.databinding.GenreLayoutBinding
import com.example.moviedb.model.casts.Cast
import com.example.moviedb.model.detail.Genre
import com.example.moviedb.model.similar.Result
import com.example.moviedb.popularmovies.details.GenreAdapter
import com.example.moviedb.utils.DiffUtils

class CastAdapter( private val clickListener: OnItemClickListener): RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    private var castList = ArrayList<Cast>()


    class ViewHolder(val binding: CastRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return CastAdapter.ViewHolder(
            CastRowLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500" + castList[position].profilePath)
            .into(holder.binding.ivProfilepath)

        holder.binding.tvName.text = castList[position].originalName
        holder.binding.tvCastingName.text = castList[position].character


        holder.itemView.setOnClickListener {
            clickListener.onItemTabCLick(castList[position])
        }


    }

    override fun getItemCount(): Int {
        return castList.size
    }


    fun setData(newData:  List<Cast>){
        val castDiffUtil =
            DiffUtils(castList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(castDiffUtil)
        castList = newData as ArrayList<Cast>
        diffUtilResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemTabCLick(casts : Cast)
    }
}