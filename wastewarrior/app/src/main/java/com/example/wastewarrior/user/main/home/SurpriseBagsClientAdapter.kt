package com.example.wastewarrior.user.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wastewarrior.R
import com.example.wastewarrior.models.SurpriseBag
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class SurpriseBagsClientAdapter(
    private val surpriseBags: List<SurpriseBag>,
) : RecyclerView.Adapter<SurpriseBagsClientAdapter.SurpriseBagViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    inner class SurpriseBagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(surpriseBag: SurpriseBag) {
            val itemNameTextView = itemView.findViewById<TextView>(R.id.itemName)
            val itemQuantityTextView = itemView.findViewById<TextView>(R.id.itemQuantity)
            val itemPriceTextView = itemView.findViewById<TextView>(R.id.itemPrice)

            itemView.findViewById<CardView>(R.id.orderCard).setOnClickListener {
                addOrderToRestaurantFirestore(surpriseBag, itemView.context)
            }

            itemView.findViewById<ImageView>(R.id.imageView4).setOnClickListener {
                val userDocumentRef =
                    FirebaseAuth.getInstance().currentUser?.let { it1 ->
                        db.collection("users").document(
                            it1.uid)
                    }
                if (userDocumentRef != null) {
                    addFavoriteToFirestore(itemView,surpriseBag, itemView.context)
                }
            }

            itemNameTextView.text = surpriseBag.name
            itemQuantityTextView.text = "${surpriseBag.quantity} left"
            itemPriceTextView.text = "£${surpriseBag.price}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurpriseBagViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_surprise_bag_client, parent, false)
        return SurpriseBagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SurpriseBagViewHolder, position: Int) {
        holder.bind(surpriseBags[position])
    }

    override fun getItemCount(): Int = surpriseBags.size

    private fun addOrderToRestaurantFirestore(surpriseBag: SurpriseBag, context: Context) {
        val ordersCollectionRef = db.collection("restaurants")
            .document(surpriseBag.restaurant) // Use the provided restaurantId

        // Retrieve the existing orders list and update it
        ordersCollectionRef.collection("orders").add(
            hashMapOf(
                "user_id" to FirebaseAuth.getInstance().currentUser?.uid, // Use the provided userId
                "order_details" to surpriseBag.toHashMap()
            )
        ).addOnSuccessListener { documentReference ->
            showToast("Order added successfully", context)
        }.addOnFailureListener { exception ->
            showToast("Failed to add order: ${exception.message}", context)
        }
    }

    private fun addFavoriteToFirestore(itemView: View, surpriseBag: SurpriseBag, context: Context) {
        val restaurantRef =
            FirebaseAuth.getInstance().currentUser?.uid?.let { db.collection("users").document(it) }

        // Retrieve the existing favorites list and update it
        if (restaurantRef != null) {
            restaurantRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    addFavoriteToExistingUserDocument(itemView,restaurantRef, surpriseBag, context)

                } else {
                    createAndAddFavoriteToUserDocument(itemView,restaurantRef, surpriseBag, context)
                }
            }.addOnFailureListener { exception ->
                showToast("Error: ${exception.message}", context)
            }
        }
    }


    private fun addFavoriteToExistingUserDocument(
        itemView: View,
        userDocumentRef: DocumentReference,
        surpriseBag: SurpriseBag,
        context: Context
    ) {
        // Retrieve the existing favorites list and update it
        userDocumentRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val favorites = documentSnapshot.get("favorites") as MutableList<HashMap<String, Any>>?
                favorites?.add(surpriseBag.toHashMap())
                favorites?.let {
                    userDocumentRef.update("favorites", it)
                        .addOnSuccessListener {
                            showToast("Added to favorites", context)
                            itemView.findNavController().navigate(R.id.action_navigation_home_to_favouritesFragment)
                        }
                        .addOnFailureListener { exception ->
                            showToast("Failed to add to favorites: ${exception.message}", context)
                        }
                }
            } else {
                showToast("User document not found", context)
            }
        }.addOnFailureListener { exception ->
            showToast("Error: ${exception.message}", context)
        }
    }

    private fun createAndAddFavoriteToUserDocument(
        itemView: View,
        userDocumentRef: DocumentReference,
        surpriseBag: SurpriseBag,
        context: Context
    ) {
        // Create a data map with initial data, including the favorite
        val initialData = hashMapOf(
            "favorites" to mutableListOf(surpriseBag.toHashMap())
            // You can add other initial fields here if needed
        )

        // Set the data on the user document using merge option to create it if not found
        userDocumentRef.set(initialData, SetOptions.merge())
            .addOnSuccessListener {
                showToast("User document created and favorite added", context)
                itemView.findNavController().navigate(R.id.action_navigation_home_to_favouritesFragment)

            }
            .addOnFailureListener { exception ->
                showToast("Failed to create user document: ${exception.message}", context)
            }
    }

    private fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
