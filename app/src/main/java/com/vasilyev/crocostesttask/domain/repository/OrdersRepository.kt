package com.vasilyev.crocostesttask.domain.repository

import com.vasilyev.crocostesttask.domain.model.order.Order

interface OrdersRepository {
    fun getOrderList(): List<Order>

    fun getOrderByOrderId(orderId: Long): Order
}