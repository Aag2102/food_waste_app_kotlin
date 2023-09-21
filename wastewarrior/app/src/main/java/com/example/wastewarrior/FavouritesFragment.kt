package com.example.wastewarrior

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wastewarrior.databinding.FragmentDashboardBinding
import com.example.wastewarrior.databinding.FragmentFavouritesBinding
import com.example.wastewarrior.models.FavoriteItem
import com.example.wastewarrior.user.main.dashboard.FavoritesAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val root: View = _binding!!.root
        // Assuming you have a reference to Firestore and the RecyclerView in your activity or fragment
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let { uid ->
            val userDocumentRef = db.collection("users").document(uid)

            // Retrieve the user's favorites from Firestore
            userDocumentRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val favoritesList = documentSnapshot.get("favorites") as List<HashMap<String, Any>>?
                    val favorites = favoritesList?.map { favoriteMap ->
                        FavoriteItem(
                            name = favoriteMap["name"] as String,
                            category = favoriteMap["category"] as String,
                            quantity = (favoriteMap["quantity"] as Long).toInt(),
                            price = (favoriteMap["price"] as Double)
                            // Map other properties as needed
                        )
                    } ?: emptyList()

                    // Set up the RecyclerView and adapter
                    val recyclerView: RecyclerView = _binding!!.recyclerView
                    val adapter = FavoritesAdapter(favorites.distinct())
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                } else {
                    showToast("User document not found", requireContext())
                }
            }.addOnFailureListener { exception ->
                showToast("Error: ${exception.message}",requireContext())
            }
        }

        return root
    }

    private fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}