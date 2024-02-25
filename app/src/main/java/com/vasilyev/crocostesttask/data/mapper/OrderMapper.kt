package com.vasilyev.crocostesttask.data.mapper

import com.vasilyev.crocostesttask.data.entity.order.OrderEntity
import com.vasilyev.crocostesttask.domain.model.order.Order

class OrderMapper {
    fun mapEntityToModel(orderEntity: OrderEntity): Order{
        return Order(
            orderId = orderEntity.orderId,
            orderTitle = orderEntity.orderTitle,
            orderDate =  orderEntity.orderDate,
            orderStatus = orderEntity.orderStatus,
            deliveryFee = orderEntity.deliveryFee,
            deliveryAddress = orderEntity.deliveryAddress,
            deliveryTime = orderEntity.deliveryTime,
            orderPreviewImage = orderEntity.orderPreviewImage,
            restaurantAddress = orderEntity.restaurantAddress,
            count = orderEntity.count,
            subTotal = orderEntity.subTotal,
            taxes = orderEntity.taxes,
            total = orderEntity.total,
            orderedTime = orderEntity.orderedTime,
            packedTime = orderEntity.packedTime,
            deliveredTime = orderEntity.deliveredTime
        )
    }
}