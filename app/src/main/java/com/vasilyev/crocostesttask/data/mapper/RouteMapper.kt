package com.vasilyev.crocostesttask.data.mapper

import com.vasilyev.crocostesttask.data.entity.route.DirectionResponse
import com.vasilyev.crocostesttask.data.entity.route.EndLocation
import com.vasilyev.crocostesttask.data.entity.route.StartLocation
import com.vasilyev.crocostesttask.domain.model.route.EndLocationModel
import com.vasilyev.crocostesttask.domain.model.route.Route
import com.vasilyev.crocostesttask.domain.model.route.StartLocationModel

class RouteMapper {
    fun mapResponseToRoute(entity: DirectionResponse): Route {
        val startLocation = mapResponseStartLocationToModel(entity.routes[0].legs[0].start_location)
        val endLocation = mapResponseEndLocationToModel(entity.routes[0].legs[0].end_location)
        val route = entity.routes[0].overview_polyline.points

        return Route(startLocation, endLocation, route)
    }

    private fun mapResponseStartLocationToModel(start: StartLocation): StartLocationModel {
        return StartLocationModel(
            lat = start.lat,
            lng = start.lng
        )
    }

    private fun mapResponseEndLocationToModel(end: EndLocation): EndLocationModel {
        return EndLocationModel(
            lat = end.lat,
            lng = end.lng
        )
    }
}