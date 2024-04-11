package com.exco.hostapp.integration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exco.hostapp.integration.util.TextUtils
import com.exco.hosttapp.integration.databinding.FragmentDefinedPlayerNoneBinding

class PlayerDefinedFragmentWithScrollMiniplayerNone : Fragment() {

    private var _binding: FragmentDefinedPlayerNoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDefinedPlayerNoneBinding.inflate(inflater, container, false)
        binding.dummyTextView.text = TextUtils.DUMMY_TEXT
        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
