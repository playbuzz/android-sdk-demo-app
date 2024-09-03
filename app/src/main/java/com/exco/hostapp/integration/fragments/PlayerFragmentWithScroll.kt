package com.exco.hostapp.integration.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.exco.hostapp.integration.util.TextUtils
import com.exco.hostapp.integration.databinding.FragmentPlayerScrollBinding
import com.exco.hostapp.integration.util.Constants
import com.exco.player.configuration.PlayerConfiguration

class PlayerFragmentWithScroll : Fragment() {

    private var _binding: FragmentPlayerScrollBinding? = null
    private val binding get() = _binding!!

    private lateinit var configuration: PlayerConfiguration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlayerScrollBinding.inflate(inflater, container, false)
        binding.dummyTextView.text = TextUtils.DUMMY_TEXT
        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        configuration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.CONFIG_BUNDLE_KEY, PlayerConfiguration::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(Constants.CONFIG_BUNDLE_KEY)
        } ?: TestConfiguration.configuration

        setupPlayer()
    }

    private fun setupPlayer() = with(binding) {
        player.loadPlayer(configuration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
