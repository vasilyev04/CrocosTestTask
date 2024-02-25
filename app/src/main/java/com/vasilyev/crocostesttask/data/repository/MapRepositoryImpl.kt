package com.vasilyev.crocostesttask.data.repository

import android.util.Log
import com.skydoves.retrofit.adapters.result.onSuccessSuspend
import com.vasilyev.crocostesttask.data.mapper.RouteMapper
import com.vasilyev.crocostesttask.data.datasource.remote.retrofit.RetrofitInstance
import com.vasilyev.crocostesttask.domain.model.order.Order
import com.vasilyev.crocostesttask.domain.model.route.Route
import com.vasilyev.crocostesttask.domain.repository.MapRepository

object MapRepositoryImpl : MapRepository {
    private val routeMapper = RouteMapper()
    private val mapApi = RetrofitInstance.api

    override suspend fun getRoute(origin: String, destination: String): Result<Route> {
        val result = mapApi.getDirections(origin, destination)

        if(result.isSuccess){
            val response = result.getOrNull()
                ?: return Result.failure(RuntimeException("Response body is null"))
            val route = routeMapper.mapResponseToRoute(response)

            return Result.success(route)
        }else{
            return Result.failure(result.exceptionOrNull() ?: RuntimeException("Unknown error"))
        }
    }
}