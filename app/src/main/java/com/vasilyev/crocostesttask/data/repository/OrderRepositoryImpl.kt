package com.vasilyev.crocostesttask.data.repository

import com.vasilyev.crocostesttask.R
import com.vasilyev.crocostesttask.data.entity.order.OrderEntity
import com.vasilyev.crocostesttask.data.mapper.OrderMapper
import com.vasilyev.crocostesttask.domain.model.order.Order
import com.vasilyev.crocostesttask.domain.model.order.OrderStatus
import com.vasilyev.crocostesttask.domain.repository.OrdersRepository

object OrderRepositoryImpl : OrdersRepository{
    private val ordersList = mutableListOf<Order>()
    private val mapper = OrderMapper()

    override fun getOrderList(): List<Order> {
        ordersList.clear()

        val orderNoodles = OrderEntity(
            orderId = 1876,
            orderDate = System.currentTimeMillis(),
            orderTitle = "Chicken Noodles",
            count = 3,
            deliveryTime = "5:00 PM",
            restaurantAddress = "4CHJ+86C, Astana 01000",
            deliveryAddress = "Astana, Qabanbay Batyr 60A/6",
            subTotal = 3897,
            taxes = 100,
            deliveryFee = 500,
            total = 4497,
            orderStatus = OrderStatus.PACKED,
            orderPreviewImage = R.drawable.img_noodles,
            orderedTime = "4:20 PM, 26 Feb 2024",
            packedTime = "4:31 PM, 26 Feb 2024"
        )

        val orderKebab = OrderEntity(
            orderId = 1877,
            orderDate = System.currentTimeMillis(),
            orderTitle = "Kebab",
            count = 1,
            deliveryTime = "5:25 PM",
            restaurantAddress = "Улица Санжара Асфендиярова, дом 5 (между 7 и 8 подъездами, Astana 020000",
            deliveryAddress = "Astana, Qabanbay Batyr 60A/6",
            subTotal = 2100,
            taxes = 100,
            deliveryFee = 600,
            total = 2800,
            orderStatus = OrderStatus.ORDERED,
            orderPreviewImage = R.drawable.img_kebab,
            orderedTime = "4:44 PM, 26 Feb 2024")

        val orderPizza = OrderEntity(
            orderId = 1878,
            orderDate = System.currentTimeMillis(),
            orderTitle = "Pizza",
            count = 2,
            deliveryTime = "5:38 PM",
            restaurantAddress = "HighVill Block B, Ahmeta Bajtursynova St 3, Astana 010000",
            deliveryAddress = "Astana, Qabanbay Batyr 60A/6",
            subTotal = 3000,
            taxes = 100,
            deliveryFee = 800,
            total = 3900,
            orderStatus = OrderStatus.DELIVERED,
            orderPreviewImage = R.drawable.img_pizza,
            orderedTime = "4:30 PM, 26 Feb 2024",
            packedTime = "4:45 PM, 26 Feb 2024",
            deliveredTime = "5:38 PM, 26 Feb 2024",
        )

        with(ordersList){
            add(mapper.mapEntityToModel(orderNoodles))
            add(mapper.mapEntityToModel(orderKebab))
            add(mapper.mapEntityToModel(orderPizza))
        }

        return ordersList.toList()
    }

    override fun getOrderByOrderId(orderId: Long): Order {

        return ordersList.find { it.orderId == orderId } as Order
    }
}