package com.example.animat.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animat.R
import com.example.animat.models.Anime
import java.net.URL


class MatchedAnimeAdapter(matchedAnimes: ArrayList<Anime>, contexto : Context) :
        RecyclerView.Adapter<MatchedAnimeAdapter.ContenedorDeVista> () {
            var innerMatchedAnimes : ArrayList<Anime> = matchedAnimes
            var innerContext : Context = contexto


        inner class ContenedorDeVista(view: View) :
                RecyclerView.ViewHolder(view), View.OnClickListener {
                    val ivPoster : ImageView
                    val tvAnimeName : TextView
                    val tvAño : TextView
                    val tvDemografia : TextView

            init {
                ivPoster = view.findViewById(R.id.ivPoster)
                tvAnimeName = view.findViewById(R.id.tvAnimeName)
                tvAño = view.findViewById(R.id.tvAño)
                tvDemografia = view.findViewById(R.id.tvDemografia)

                    }
            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContenedorDeVista {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_matched_anime_recycler, parent, false)

        return ContenedorDeVista(view)
    }

    override fun getItemCount(): Int {
        return innerMatchedAnimes.size
    }

    override fun onBindViewHolder(holder: ContenedorDeVista, position: Int) {
        val anime: Anime = innerMatchedAnimes.get(position)
        val uri = Uri.parse(anime.image)
        val firstGenre = anime.genres.get(0)
        holder.ivPoster.setImageURI(uri)
        holder.tvAnimeName.text = anime.title
        holder.tvDemografia.text = firstGenre
        holder.tvAño.text = "2015"
    }
}