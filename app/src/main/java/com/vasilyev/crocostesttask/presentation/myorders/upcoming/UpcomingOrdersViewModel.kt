package com.vasilyev.crocostesttask.presentation.myorders.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vasilyev.crocostesttask.data.repository.OrderRepositoryImpl
import com.vasilyev.crocostesttask.domain.model.order.Order
import com.vasilyev.crocostesttask.domain.usecase.GetOrderListUseCase

class UpcomingOrdersViewModel : ViewModel(){
    private val repository = OrderRepositoryImpl

    private val getOrderListUseCase = GetOrderListUseCase(repository)

    private val _orderList: MutableLiveData<List<Order>> = MutableLiveData()
    val orderList: LiveData<List<Order>>
        get() = _orderList

    fun getOrders(){
        _orderList.value = getOrderListUseCase.invoke()
    }
}