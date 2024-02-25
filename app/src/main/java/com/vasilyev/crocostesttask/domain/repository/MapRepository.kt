package com.vasilyev.crocostesttask.domain.repository

import com.vasilyev.crocostesttask.domain.model.route.Route

interface MapRepository {

    suspend fun getRoute(origin: String, destination: String): Result<Route>
}