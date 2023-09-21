package com.example.wastewarrior.admin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wastewarrior.R
import com.example.wastewarrior.models.SurpriseBag
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SurpriseBagAdapter(private val surpriseBags: List<SurpriseBag>) :
    RecyclerView.Adapter<SurpriseBagAdapter.SurpriseBagViewHolder>() {
    private val db = FirebaseFirestore.getInstance()
    inner class SurpriseBagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(surpriseBag: SurpriseBag) {
            val itemNameTextView = itemView.findViewById<TextView>(R.id.itemName)
            val itemQuantityTextView = itemView.findViewById<TextView>(R.id.itemQuantity)
            val itemIsFavouriteTextView = itemView.findViewById<TextView>(R.id.itemIsFavourite)
            val itemPriceTextView = itemView.findViewById<TextView>(R.id.itemPrice)
            itemView.findViewById<CardView>(R.id.add).setOnClickListener {
                updateSurpriseBagByName(surpriseBag,itemView.context)
            }

            itemNameTextView.text = surpriseBag.name
            itemQuantityTextView.text = surpriseBag.quantity.toString()+" left"
            itemIsFavouriteTextView.text = if (surpriseBag.isFavourite) "Favourite" else "Not Favourite"
            itemPriceTextView.text = "£${surpriseBag.price}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurpriseBagViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_surprise_bag, parent, false)
        return SurpriseBagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SurpriseBagViewHolder, position: Int) {
        holder.bind(surpriseBags[position])
    }

    override fun getItemCount(): Int = surpriseBags.size

    private fun updateSurpriseBagByName(updatedSurpriseBag: SurpriseBag,context:Context) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let {
            val restaurantRef = db.collection("restaurants").document(it)

            // Retrieve the existing surprises list and update the specific surprise bag by name
            restaurantRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val surprises = documentSnapshot.get("surprises") as MutableList<HashMap<String, Any>>?
                    surprises?.let {
                        // Find the index of the surprise bag to be updated based on its name
                        val indexToUpdate = it.indexOfFirst { surprise ->
                            surprise["name"] == updatedSurpriseBag.name
                        }

                        if (indexToUpdate != -1) {
                            val currentQuantity = it[indexToUpdate]["quantity"] as Long
                            it[indexToUpdate]["quantity"] = currentQuantity + 1



                            restaurantRef.update("surprises", it)
                                .addOnSuccessListener {
                                    showToast("Surprise bag updated successfully",context)
                                    val intent = Intent(context, SurpriseBagsActivity::class.java)
                                    context.startActivity(intent)
                                }
                                .addOnFailureListener { exception ->
                                    showToast("Failed to update surprise bag",context)
                                }
                        } else {
                            showToast("Surprise bag not found",context)
                        }
                    }
                } else {
                    showToast("Restaurant document not found",context)
                }
            }.addOnFailureListener { exception ->
                showToast("Error: ${exception.message}",context)
            }
        }
    }

    private fun showToast(message: String,context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
