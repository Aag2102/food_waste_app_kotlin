package com.example.wastewarrior.user.main.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wastewarrior.R
import com.example.wastewarrior.models.FavoriteItem

class FavoritesAdapter(private val favorites: List<FavoriteItem>) :
    RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize UI elements in the ViewHolder
        val favoriteNameTextView: TextView = itemView.findViewById(R.id.favoriteNameTextView)
        val favoritePrice: TextView = itemView.findViewById(R.id.favoritePrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        // Bind favorite data to UI elements in the ViewHolder
        val favorite = favorites[position]
        holder.favoriteNameTextView.text = favorite.name
        holder.favoritePrice.text = favorite.price.toString()
        // Bind other properties to UI elements as needed
    }

    override fun getItemCount(): Int {
        return favorites.size
    }
}
