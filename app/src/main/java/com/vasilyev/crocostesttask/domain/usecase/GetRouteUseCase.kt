package com.vasilyev.crocostesttask.domain.usecase

import com.vasilyev.crocostesttask.domain.model.route.Route
import com.vasilyev.crocostesttask.domain.repository.MapRepository

class GetRouteUseCase(private val repository: MapRepository){

    suspend fun invoke(origin: String, destination: String): Route {
        return repository.getRoute(origin, destination)
    }
}