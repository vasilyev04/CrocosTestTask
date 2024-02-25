package com.vasilyev.crocostesttask.data.entity.order

import com.vasilyev.crocostesttask.domain.model.order.OrderStatus

data class OrderEntity(
    val orderId: Long,
    val orderDate: Long,
    val orderTitle: String,
    val deliveryTime: String,
    val restaurantAddress: String,
    val deliveryAddress: String,
    val count: Int,
    val subTotal: Int,
    val taxes: Int,
    val deliveryFee: Int,
    val total: Int,
    val orderStatus: OrderStatus,
    val orderPreviewImage: Int,
    val orderedTime: String = "",
    val packedTime: String = "",
    val deliveredTime: String = ""
){
    companion object{
        const val UNDEFINED_ID = -1L
    }
}