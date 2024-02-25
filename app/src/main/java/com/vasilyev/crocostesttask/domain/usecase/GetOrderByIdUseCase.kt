package com.vasilyev.crocostesttask.domain.usecase

import com.vasilyev.crocostesttask.domain.model.order.Order
import com.vasilyev.crocostesttask.domain.repository.OrdersRepository

class GetOrderByIdUseCase(private val repository: OrdersRepository) {

    fun invoke(order: Long): Order{
        return repository.getOrderByOrderId(order)
    }
}