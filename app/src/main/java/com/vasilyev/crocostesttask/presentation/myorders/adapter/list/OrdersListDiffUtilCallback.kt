package com.vasilyev.crocostesttask.presentation.myorders.adapter.list

import androidx.recyclerview.widget.DiffUtil
import com.vasilyev.crocostesttask.domain.model.order.Order

class OrdersListDiffUtilCallback: DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.orderId == newItem.orderId
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}