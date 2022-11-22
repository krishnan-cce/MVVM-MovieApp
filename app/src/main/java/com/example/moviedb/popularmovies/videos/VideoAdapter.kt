package com.example.moviedb.popularmovies.videos


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.databinding.VideosRowLayoutBinding
import com.example.moviedb.model.videos.Videos
import com.example.moviedb.utils.DiffUtils


class VideoAdapter( private val clickListener: OnItemClickListener): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    private var videoList = ArrayList<Videos>()

    class ViewHolder(val binding: VideosRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return VideoAdapter.ViewHolder(
            VideosRowLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load("https://i.ytimg.com/vi/" + videoList[position].key + "/0.jpg")
            .into(holder.binding.ivThumbnail)

        holder.itemView.setOnClickListener {
            clickListener.onItemVideoCLick(videoList[position])
        }

    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun setData(newData:  List<Videos>){
        val videoDiffUtil =
            DiffUtils(videoList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(videoDiffUtil)
        videoList = newData as ArrayList<Videos>
        diffUtilResult.dispatchUpdatesTo(this)
    }


    interface OnItemClickListener {
        fun onItemVideoCLick(movies : Videos)
    }
}