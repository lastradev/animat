package com.example.animat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animat.R
import com.example.animat.models.Anime
import com.squareup.picasso.Picasso

class MatchedAnimeAdapter(matchedAnimes: ArrayList<Anime>) :
    RecyclerView.Adapter<MatchedAnimeAdapter.ViewContainer>() {
    private var innerMatchedAnimes: ArrayList<Anime> = matchedAnimes

    inner class ViewContainer(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        val ivPoster: ImageView
        val tvAnimeName: TextView
        val tvYear: TextView
        val tvGenre: TextView

        init {
            ivPoster = view.findViewById(R.id.ivPoster)
            tvAnimeName = view.findViewById(R.id.tvAnimeName)
            tvYear = view.findViewById(R.id.tvAño)
            tvGenre = view.findViewById(R.id.tvDemografia)
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewContainer {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_matched_anime_recycler, parent, false)

        return ViewContainer(view)
    }

    override fun getItemCount(): Int {
        return innerMatchedAnimes.size
    }

    override fun onBindViewHolder(holder: ViewContainer, position: Int) {
        val anime: Anime = innerMatchedAnimes[position]
        val firstGenre = anime.genres[0]

        holder.tvAnimeName.text = anime.title
        holder.tvGenre.text = firstGenre
        holder.tvYear.text = "2015"

        Picasso.get().load(anime.image).into(holder.ivPoster)
    }
}