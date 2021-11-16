package com.example.miniproject.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.miniproject.R
import com.example.miniproject.databinding.FragmentPhotoDetailBinding
import com.example.miniproject.service.SessionManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment : Fragment(),  SessionManager.LogoutListener {

    private lateinit var binding: FragmentPhotoDetailBinding
    private val args: PhotoDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        SessionManager.registerSessionListener(this)

        Picasso.get().load(args.url).into(binding.imageShowView)
        binding.imageName.text = args.name
        binding.imageDesc.text = args.desc

        binding.imageShowLayout.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }

        return binding.root

    }

    override fun doLogout() {
        activity?.runOnUiThread {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_photoDetailFragment_to_loginFragment)
        }
    }

}