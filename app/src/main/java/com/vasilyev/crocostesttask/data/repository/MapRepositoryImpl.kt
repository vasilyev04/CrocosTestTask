package com.vasilyev.crocostesttask.data.repository

import android.util.Log
import com.vasilyev.crocostesttask.data.mapper.RouteMapper
import com.vasilyev.crocostesttask.data.datasource.remote.retrofit.RetrofitInstance
import com.vasilyev.crocostesttask.domain.model.route.Route
import com.vasilyev.crocostesttask.domain.repository.MapRepository

object MapRepositoryImpl : MapRepository {
    private val routeMapper = RouteMapper()

    override suspend fun getRoute(origin: String, destination: String): Route {
        val directionResponse = RetrofitInstance.api.getDirections(origin, destination).body()
            ?: throw RuntimeException("DirectionResponse hasn't a body")

        Log.d("Direction Response", "getRoute: $directionResponse")

        return routeMapper.mapResponseToRoute(directionResponse)
    }
}