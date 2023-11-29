package com.exco.hostapp.integration.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.exco.player.configuration.PlayerConfiguration
import com.exco.hostapp.integration.util.Constants
import com.exco.hostapp.integration.util.SampleXmlLogger
import com.exco.hostapp.integration.util.TextViewLogger
import com.exco.hosttapp.integration.databinding.FragmentPlayerLogsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerFragmentWithLogs : Fragment() {

    private var _binding: FragmentPlayerLogsBinding? = null
    private val binding get() = _binding!!

    private lateinit var configuration: PlayerConfiguration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlayerLogsBinding.inflate(inflater, container, false)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        val textViewLogger = TextViewLogger()
        binding.dynamicEmbeddedExcoPlayer.publicListener = SampleXmlLogger(textViewLogger)
        viewLifecycleOwner.lifecycleScope.launch {
            textViewLogger.logFlow.collect{
                binding.tvLogs.text = it
            }
        }
        return binding.root
    }
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()

        configuration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(Constants.CONFIG_BUNDLE_KEY, PlayerConfiguration::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(Constants.CONFIG_BUNDLE_KEY)
        } ?: PlayerConfiguration.TEST_CONFIGURATION
        setupPlayer()
    }
    private fun setupPlayer() =
        with(binding) {
            dynamicEmbeddedExcoPlayer.loadPlayer(configuration)
        }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

