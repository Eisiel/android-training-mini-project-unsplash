package com.example.miniproject.ui.logout

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.miniproject.R
import com.example.miniproject.api.LoginServices
import com.example.miniproject.databinding.FragmentLoginBinding
import com.example.miniproject.databinding.FragmentLogoutBinding
import com.example.miniproject.service.AESEncrypts
import com.example.miniproject.service.SessionManager
import com.example.miniproject.ui.home.AppBarState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class LogoutFragment : Fragment() {

    private var _binding: FragmentLogoutBinding? = null

    @Inject
    lateinit var provideretrofitLogin: LoginServices

    @Inject
    lateinit var aesEncryp: AESEncrypts

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        binding.logout.setOnClickListener {
            // clear preference
            val prefs: SharedPreferences = requireActivity().getSharedPreferences("LOGIN_DATA", Context.MODE_PRIVATE)
            prefs.edit(commit = true) {
                clear()
            }
            findNavController(binding.root)
                .navigate(R.id.action_logoutFragment_to_loginFragment)


        }
        return binding.root
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    public fun sessionDone(){
        findNavController().navigate(R.id.loginFragment)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}