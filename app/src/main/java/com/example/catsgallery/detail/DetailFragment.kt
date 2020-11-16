package com.example.catsgallery.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.catsgallery.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val theCatSearchResponseItem = DetailFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val viewModelFactory = DetailViewModelFactory(theCatSearchResponseItem, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(DetailViewModel::class.java)
        return binding.root
    }
}
