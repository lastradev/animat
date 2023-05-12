package com.example.animat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.animat.MATCHED_ANIMES_KEY
import com.example.animat.R
import com.example.animat.models.Anime
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso

class MatchedAnimeAdapter(matchedAnimes: ArrayList<Anime>, context: Context) :
    RecyclerView.Adapter<MatchedAnimeAdapter.ViewContainer>() {
    private var innerMatchedAnimes: ArrayList<Anime> = matchedAnimes
    private var innerContext: Context = context
    inner class ViewContainer(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        val ivPoster: ImageView
        val tvAnimeName: TextView
        val tvGenre: TextView
        val fabDelete: FloatingActionButton

        init {
            ivPoster = view.findViewById(R.id.ivPoster)
            tvAnimeName = view.findViewById(R.id.tvAnimeName)
            tvGenre = view.findViewById(R.id.tvDemografia)
            fabDelete = view.findViewById(R.id.fabDelete)

            fabDelete.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if(adapterPosition>=0) {
                val anime: Anime = innerMatchedAnimes.get(adapterPosition)
                val sharedPreferences = innerContext.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
                val gson = Gson()
                val matchedAnimesJson = sharedPreferences.getString(MATCHED_ANIMES_KEY, null)
                if (matchedAnimesJson != null) {
                    val type = object : TypeToken<ArrayList<Anime>>() {}.type
                    val matchedAnimes: ArrayList<Anime> = gson.fromJson(matchedAnimesJson, type)
                    val filteredMatchedAnimes = matchedAnimes.filter { it.id != anime.id }
                    val newMatchedAnimesJson = gson.toJson(filteredMatchedAnimes)
                    sharedPreferences.edit().putString(MATCHED_ANIMES_KEY, newMatchedAnimesJson).apply()
                    innerMatchedAnimes.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                Toast.makeText(innerContext, "${anime.title} eliminado", Toast.LENGTH_LONG).show()
            }
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

        Picasso.get().load(anime.image).into(holder.ivPoster)
    }
}