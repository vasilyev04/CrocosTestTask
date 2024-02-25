package com.vasilyev.crocostesttask.data.entity.route

data class DirectionResponse(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)