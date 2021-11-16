package com.example.miniproject.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.miniproject.R
import com.example.miniproject.api.LoginServices
import com.example.miniproject.databinding.FragmentLoginBinding
import com.example.miniproject.service.AESEncrypts
import com.example.miniproject.service.SessionManager
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
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

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

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val password = binding.userPassword.editText?.text.toString()

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
                binding.progressLoader.visibility = View.VISIBLE

                lifecycleScope.launch {
                    try {
                        // send post
                        val apiResponse = provideretrofitLogin.login("454041184B0240FBA3AACD15A1F7A8BB",email, password)
                        val results = apiResponse.data
                        // encrypt
                        val encryptResult = aesEncryp.encryptData(results.toString())
                        // Preferench
                        val sharedPreference =  requireActivity().getSharedPreferences("LOGIN_DATA", Context.MODE_PRIVATE)
                        var editor = sharedPreference.edit()
                        editor.putString("LoginData", encryptResult.toString())
                        editor.putLong("l",100L)
                        editor.commit()
                        SessionManager.resetSession()
                        // loading bar
                        binding.progressLoader.visibility = View.INVISIBLE
                        // navigate
                        //view.findNavController().navigate(R.id.action_loginFragment_to_unsplashSearchFragment)
                        view.findNavController().navigate(R.id.action_loginFragment_to_unsplashSearchFragment)
                    }catch (exception: IOException) {
                        //error log
                        Log.d("Login", "Error IOException: ${exception.localizedMessage}")
                        Snackbar.make(view, exception.localizedMessage, Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.RED)
                            .setAction("Action", null).show()
                        binding.progressLoader.visibility = View.INVISIBLE
                    } catch (exception: HttpException) {
                        // error log
                        Log.d("Login", "Error HttpException: ${exception.localizedMessage}")
                        Snackbar.make(view, "Password atau Email anda salah!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.RED)
                            .setAction("Action", null).show()
                        binding.progressLoader.visibility = View.INVISIBLE
                    }
                }
            }

        }
    }
    public fun sessionDone(){
        findNavController().navigate(R.id.loginFragment)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}