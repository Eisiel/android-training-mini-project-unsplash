package com.example.taskweek2.ui.login

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskweek2.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import android.R
//import com.example.taskweek2.retrofit.LoginRetrofit


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val password = binding.userPassword.editText?.text.toString()
            Log.d("Debug Login", "username: $email")
            Log.d("Debug Login", "password: $password")
            if (email == "" || password == ""){
                var varNull = "Password"
                if (email == "" && password == ""){
                    varNull = "Email dan Password"
                }else if (email == ""){
                    varNull = "Username"
                }
                Snackbar.make(view, "Harap Isi $varNull anda", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED)
                    .setAction("Action", null).show()
            }else{

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}