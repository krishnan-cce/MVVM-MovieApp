package com.example.moviedb.popularmovies.similar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.databinding.SimilarRowLayoutBinding
import com.example.moviedb.databinding.VideosRowLayoutBinding
import com.example.moviedb.model.similar.Result
import com.example.moviedb.model.videos.Videos
import com.example.moviedb.popularmovies.details.PopularMovieDetailsActivity
import com.example.moviedb.popularmovies.videos.VideoAdapter
import com.example.moviedb.utils.DiffUtils

class SimilarAdapter( private val clickListener: OnItemClickListener,private val mContext: Context)
    : RecyclerView.Adapter<SimilarAdapter.ViewHolder>()  {
    private var similarList = ArrayList<Result>()

    class ViewHolder(val binding: SimilarRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return SimilarAdapter.ViewHolder(
            SimilarRowLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500" + similarList[position].posterPath)
            .into(holder.binding.ivProfilepath)

        holder.binding.tvName.text = similarList[position].title
        holder.binding.tvCastingName.text = similarList[position].originalTitle


        holder.itemView.setOnClickListener {
            clickListener.onItemEditCLick(similarList[position])
        }
//        if(itemCount < 1){
//            holder.binding.tvName.visibility = View.GONE
//            holder.binding.tvCastingName.visibility = View.GONE
//            holder.binding.ivProfilepath.visibility = View.GONE
//        }

    }

    override fun getItemCount(): Int {
        return similarList.size
    }

    fun setData(newData:  List<Result>){
        val similarDiffUtil =
            DiffUtils(similarList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(similarDiffUtil)
        similarList = newData as ArrayList<Result>
        diffUtilResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemEditCLick(movies : Result)
    }
}