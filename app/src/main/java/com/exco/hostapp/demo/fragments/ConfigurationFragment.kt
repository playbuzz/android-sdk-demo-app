package com.exco.hostapp.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.exco.hostapp.demo.R
import com.exco.hostapp.demo.databinding.FragmentConfigurationBinding
import com.exco.hostapp.demo.util.Constants
import com.exco.player.configuration.PlayerConfiguration

class ConfigurationFragment : Fragment() {

    private var _binding: FragmentConfigurationBinding? = null
    private val binding get() = _binding!!

    private var configuration = PlayerConfiguration.TEST_CONFIGURATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        updateUIWithConfiguration()

        binding.loadPlayerButton.setOnClickListener {
            findNavController().navigateWithConfigurationBundle(
                R.id.action_configurationFragment_to_playerFragmentLogs
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUIWithConfiguration() =
        with(binding) {
            appNameEt.setText(getString(R.string.app_name))
            appBundleEt.setText(requireContext().packageName)
            etPlayerId.setText(configuration.playerId)
        }

    private fun NavController.navigateWithConfigurationBundle(
        @IdRes destinationId: Int,
    ) = configuration.let {
        it.refreshConfigurationFromDisplay()
        val payloadBundle = bundleOf(Constants.CONFIG_BUNDLE_KEY to it)
        navigate(destinationId, payloadBundle)
    }

    private fun PlayerConfiguration.refreshConfigurationFromDisplay() =
        with(binding) {
            if(etPlayerId.text.isNotEmpty()) {
                playerId = etPlayerId.text.toString()
            }
            appCategory = if(etAppCategory.text.isNotEmpty()) {
                etAppCategory.text.toString()
            }else{
                null
            }
            appStoreId = if(etAppStoreId.text.isNotEmpty()) {
                etAppStoreId.text.toString()
            }else{
                null
            }
            appStoreUrl = if(etAppStoreUrl.text.isNotEmpty()) {
                etAppStoreUrl.text.toString()
            }else{
                null
            }
            appVersion = if(etAppVersion.text.isNotEmpty()) {
                etAppVersion.text.toString()
            }else{
                null
            }
            deviceId = if(etDeviceId.text.isNotEmpty()) {
                etDeviceId.text.toString()
            }else{
                null
            }
            ifa = if(etIfa.text.isNotEmpty()) {
                etDeviceId.text.toString()
            }else{
                null
            }
        }
}
