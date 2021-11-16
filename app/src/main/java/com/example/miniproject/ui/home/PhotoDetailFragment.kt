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
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailBinding
    val args: PhotoDetailFragmentArgs by navArgs()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        Picasso.get().load(args.url)
//            .into(view?.findViewById(image_show_view))
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)

        Picasso.get().load(args.url).into(binding.imageShowView)
        binding.imageName.text = args.name
        binding.imageDesc.text = args.desc

        binding.imageShowLayout.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }

        return binding.root
//        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }



//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PhotoDetailFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PhotoDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}