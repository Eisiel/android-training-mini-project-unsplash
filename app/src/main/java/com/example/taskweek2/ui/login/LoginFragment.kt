package com.example.taskweek2.ui.login

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.RemoteMediator
import com.example.taskweek2.api.LoginServices
import com.example.taskweek2.databinding.FragmentLoginBinding
import com.example.taskweek2.di.ApiLoginModule
import com.example.taskweek2.ui.home.PhotoAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    @Inject
    lateinit var provideretrofitLogin: LoginServices

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
                lifecycleScope.launch {
                    try {
                        val apiResponse = provideretrofitLogin.login("454041184B0240FBA3AACD15A1F7A8BB",email, password)
                        val results = apiResponse.data
                    }catch (exception: IOException) {
                        Log.d("Login", "Error IOException: ${exception.localizedMessage}")
                    } catch (exception: HttpException) {
                        Log.d("Login", "Error HttpException: ${exception.localizedMessage}")
                        Snackbar.make(view, "Password atau Email anda salah!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.RED)
                            .setAction("Action", null).show()
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}