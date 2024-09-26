package com.exco.hostapp.integration.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.exco.hostapp.integration.databinding.FragmentProgrammaticPlayerBinding
import com.exco.hostapp.integration.util.Constants
import com.exco.hostapp.integration.util.TextUtils
import com.exco.player.configuration.ConfigurationOptions
import com.exco.player.configuration.ContentVideoSettings
import com.exco.player.configuration.PlaybackMode
import com.exco.player.configuration.PlayerConfiguration
import com.exco.player.configuration.PlaylistItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgrammaticPlayerFragment : Fragment() {
    private var _binding: FragmentProgrammaticPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var configuration: PlayerConfiguration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProgrammaticPlayerBinding.inflate(inflater, container, false)

        with(binding) {
            backArrow.setOnClickListener {
                findNavController().popBackStack()
            }

            btnInitialize.setOnClickListener {
                player.init(configOptions())
            }

            btnPlay.setOnClickListener {
                player.play()
            }

            btnPause.setOnClickListener {
                player.pause()
            }

            btnSetIndex.setOnClickListener {
                if (etIndex.text.isNotEmpty()){
                    player.setPlaylistIndex(etIndex.text.toString().toInt())
                }
            }

            btnDestroy.setOnClickListener {
                player.destroy()
            }
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
        } ?: TestConfiguration.programmaticConfiguration

        setupPlayer()
    }

    private fun setupPlayer() = with(binding) {
        player.loadPlayer(configuration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configOptions() = ConfigurationOptions(
        playbackMode = PlaybackMode.CLICK_TO_PLAY,
        autoPlay = true,
        mute = false,
        showAds = true,
        customParams = mapOf("customColor" to "red"),
        content = ContentVideoSettings(
            playFirst = listOf(
                PlaylistItem(
                    id = "fa375-264b-4a86-809c-bbfc191532c1",
                    src = "https://mcd.ex.co/video/upload/v1490095101/landscape7a6fa375-264b-4a86-809c-bbfc191532c1.mp4"
                ),
                PlaylistItem(
                    id = "fa375-264b-4a86-809c-bbfc191532c2",
                    src = "https://mcd.ex.co/video/upload/v1490095101/landscape7a6fa375-264b-4a86-809c-bbfc191532c1.mp4"
                )
            ),
            tags = listOf("Sport", "Cinema"),
            playlist = listOf(
                PlaylistItem(
                    id = "fa375-264b-4a86-809c-bbfc191532c1",
                    src = "https://mcd.ex.co/video/upload/v1490095101/landscape7a6fa375-264b-4a86-809c-bbfc191532c1.mp4"
                ),
                PlaylistItem(
                    id = "fa375-264b-4a86-809c-bbfc191532c2",
                    src = "https://mcd.ex.co/video/upload/v1490095101/landscape7a6fa375-264b-4a86-809c-bbfc191532c1.mp4"
                )
            ),
        )
    )
}