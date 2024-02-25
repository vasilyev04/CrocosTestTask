package com.vasilyev.crocostesttask.presentation.myorders.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.vasilyev.crocostesttask.databinding.FragmentUpcomingOrdersBinding
import com.vasilyev.crocostesttask.presentation.details.OrderDetailsActivity
import com.vasilyev.crocostesttask.presentation.myorders.adapter.list.MyOrdersListAdapter

class UpcomingOrdersFragment : Fragment() {
    private var _binding: FragmentUpcomingOrdersBinding? = null
    private val binding: FragmentUpcomingOrdersBinding
        get() = _binding ?: throw RuntimeException("FragmentUpcomingOrdersBinding is null")

    private val viewModel: UpcomingOrdersViewModel by viewModels()

    private val ordersAdapter by lazy {
        MyOrdersListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingOrdersBinding.inflate(inflater)

        attachRecyclerView()
        observeViewModel()
        viewModel.getOrders()

        return binding.root
    }

    private fun attachRecyclerView(){
        binding.rvUpcomingOrders.adapter = ordersAdapter

        with(ordersAdapter){
            onOrderClickCallback = { order ->
                launchDetailsActivity(order.orderId)
            }
        }
    }

    private fun observeViewModel(){
        viewModel.orderList.observe(viewLifecycleOwner){ list ->
            ordersAdapter.submitList(list)
        }
    }

    private fun launchDetailsActivity(orderId: Long){
        val intent = OrderDetailsActivity.getDetailsIntent(requireActivity(), orderId)
        startActivity(intent)
    }

    companion object{
        fun newUpcomingOrdersInstance(): UpcomingOrdersFragment {
            return UpcomingOrdersFragment()
        }
    }
}