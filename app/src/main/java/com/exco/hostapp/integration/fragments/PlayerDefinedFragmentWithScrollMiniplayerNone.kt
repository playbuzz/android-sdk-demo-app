package com.exco.hostapp.integration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.exco.hostapp.integration.util.TextUtils
import com.exco.hosttapp.integration.R
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
        binding.recyclerList.layoutManager = LinearLayoutManager(context)
        binding.recyclerList.adapter = ExCoAdapter()
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ExCoAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemViewType(position: Int): Int = when (position) {
            0 -> -1
            else -> 1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = when (viewType) {
            -1 -> VH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.exco_player_view, parent, false)
            )
            else -> VH(TextView(parent.context))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            when (position) {
                0 -> {}
                else -> holder.itemView.let { it as TextView }.text = TextUtils.SHORT_DUMMY_TEXT
            }
        }

        override fun getItemCount(): Int = 50

        inner class VH(itemView: View) : ViewHolder(itemView)
    }
}
