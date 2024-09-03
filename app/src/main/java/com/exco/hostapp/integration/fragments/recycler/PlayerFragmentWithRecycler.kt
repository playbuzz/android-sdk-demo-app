package com.exco.hostapp.integration.fragments.recycler

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.exco.hostapp.integration.fragments.recycler.adapter.RecyclerViewAdapter
import com.exco.hostapp.integration.fragments.recycler.adapter.ViewData
import com.exco.hostapp.integration.util.TextUtils
import com.exco.hostapp.integration.databinding.FragmentPlayerRecyclerBinding
import com.exco.hostapp.integration.fragments.TestConfiguration
import com.exco.hostapp.integration.util.Constants
import com.exco.player.configuration.PlayerConfiguration

class PlayerFragmentWithRecycler : Fragment() {
    private var _binding: FragmentPlayerRecyclerBinding? = null
    private val binding get() = _binding!!

    private lateinit var configuration: PlayerConfiguration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlayerRecyclerBinding.inflate(inflater, container, false)
        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        configuration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.CONFIG_BUNDLE_KEY, PlayerConfiguration::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(Constants.CONFIG_BUNDLE_KEY)
        } ?: TestConfiguration.configuration

        val layoutManager = LinearLayoutManager(context)
        val viewDataList = listOf(
            ViewData.ExCoPlayerData(configuration),
            ViewData.TextData(text = TextUtils.SHORT_DUMMY_TEXT),
            ViewData.TextData(text = TextUtils.SHORT_DUMMY_TEXT),
            ViewData.TextData(text = TextUtils.SHORT_DUMMY_TEXT),
            ViewData.TextData(text = TextUtils.SHORT_DUMMY_TEXT),
        )
        val adapter = RecyclerViewAdapter(dataList = viewDataList)
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}