package com.example.imagesearchapp.presentation.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.imagesearchapp.databinding.FragmentSplashBinding
import com.example.imagesearchapp.presentation.MainActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as MainActivity
        activity.HideStatusBar()
    }

    override fun onStop() {
        super.onStop()
        val activity = activity as MainActivity
        activity.ShowStatusBar()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
}