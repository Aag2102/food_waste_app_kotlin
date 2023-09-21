package com.example.wastewarrior.user.main.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wastewarrior.LandingPageActivity
import com.example.wastewarrior.R
import com.example.wastewarrior.RegisterActivity
import com.example.wastewarrior.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.FirebaseAuth

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.accountInfo.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notifications_to_accountInfoFragment)
        }
        binding.payment.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notifications_to_paymentFragment)
        }

        binding.orders.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notifications_to_ordersFragment)
        }
        binding.logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            context?.startActivity(Intent(context,LandingPageActivity::class.java))

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}