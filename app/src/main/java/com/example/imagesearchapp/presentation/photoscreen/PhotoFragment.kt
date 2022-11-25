package com.example.imagesearchapp.presentation.photoscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentPhotoBinding
import com.example.imagesearchapp.presentation.MainActivity
import com.example.imagesearchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private val viewModel by viewModels<PhotoViewModel>()
    private lateinit var binding: FragmentPhotoBinding
    private val args: PhotoFragmentArgs by navArgs()
    private lateinit var progressLoading: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        createProgress()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getPhoto(args.photoId)
        binding.txtUser.setOnClickListener {
            val uri = Uri.parse(viewModel.photo.value?.user?.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireContext().startActivity(intent)
        }
        binding.circleImageView.setOnClickListener {
            this.findNavController()
                .navigate(PhotoFragmentDirections.actionPhotoFragmentToUserFragment(viewModel.photo.value?.user?.username!!))
        }
        viewModel.loadingObserver.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> progressLoading.show()
                is Resource.Success -> {
                    progressLoading.dismiss()
                }
                is Resource.Failed -> {
                    progressLoading.dismiss()
                    Toast.makeText(requireContext(), response.error?.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun createProgress() {
        progressLoading = AlertDialog.Builder(requireContext())
            .setView(R.layout.progress_layout)
            .setCancelable(false)
            .create()
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        val activity = activity as MainActivity
        activity.supportActionBar?.show()
    }

}