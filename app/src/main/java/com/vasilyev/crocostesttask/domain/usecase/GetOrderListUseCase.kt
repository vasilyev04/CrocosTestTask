package com.vasilyev.crocostesttask.domain.usecase

import com.vasilyev.crocostesttask.domain.model.order.Order
import com.vasilyev.crocostesttask.domain.repository.OrdersRepository

class GetOrderListUseCase(private val repository: OrdersRepository) {

    fun invoke(): List<Order>{
        return repository.getOrderList()
    }
}