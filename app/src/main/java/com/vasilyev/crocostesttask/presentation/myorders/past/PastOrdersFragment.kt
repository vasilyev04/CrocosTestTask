package com.vasilyev.crocostesttask.presentation.myorders.past

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vasilyev.crocostesttask.databinding.FragmentPastOrdersBinding

class PastOrdersFragment : Fragment() {
    private var _binding: FragmentPastOrdersBinding? = null
    private val binding: FragmentPastOrdersBinding
        get() = _binding ?: throw RuntimeException("FragmentPastOrdersBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPastOrdersBinding.inflate(inflater)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun newPastOrdersFragmentInstance(): PastOrdersFragment {
            return PastOrdersFragment()
        }
    }
}