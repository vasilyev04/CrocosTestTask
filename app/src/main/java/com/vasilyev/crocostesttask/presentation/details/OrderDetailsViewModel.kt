package com.vasilyev.crocostesttask.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vasilyev.crocostesttask.data.repository.OrderRepositoryImpl
import com.vasilyev.crocostesttask.domain.model.order.Order
import com.vasilyev.crocostesttask.domain.usecase.GetOrderByIdUseCase

class OrderDetailsViewModel: ViewModel() {
    private val repository = OrderRepositoryImpl

    private val getOrderByIdUseCase = GetOrderByIdUseCase(repository)

    private val _order: MutableLiveData<Order> = MutableLiveData()
    val order: LiveData<Order>
        get() = _order

    fun getOrderById(orderId: Long){
        _order.value = getOrderByIdUseCase.invoke(orderId)
    }
}