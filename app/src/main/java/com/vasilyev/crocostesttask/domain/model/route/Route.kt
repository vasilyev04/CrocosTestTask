package com.vasilyev.crocostesttask.domain.model.route

data class Route(
    val startLocation: StartLocationModel,
    val endLocation: EndLocationModel,
    val route: String
)