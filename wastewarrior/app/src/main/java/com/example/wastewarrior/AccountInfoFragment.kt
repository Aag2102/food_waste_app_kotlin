package com.example.wastewarrior

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AccountInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  inflater = inflater.inflate(R.layout.fragment_account_info, container, false)
        val user = Firebase.auth.currentUser
        val usernameEditText = inflater.findViewById<EditText>(R.id.usernameEditText)
        usernameEditText.setText(user?.displayName)
        user?.displayName?.let { Log.i("Username", it) };

        inflater.findViewById<Button>(R.id.saveButton).setOnClickListener {
            val newUsername = usernameEditText.text.toString()

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newUsername)
                .build()

            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_LONG).show();
                        // Handle UI updates or show a success message
                    } else {
                        // Handle error
                        Toast.makeText(requireContext(), "Error updating profile. Please retry", Toast.LENGTH_LONG).show();
                    }
                }


        }


        return inflater;
    }


}