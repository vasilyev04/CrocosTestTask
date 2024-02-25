package com.vasilyev.crocostesttask.presentation.track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.vasilyev.crocostesttask.data.repository.MapRepositoryImpl
import com.vasilyev.crocostesttask.data.repository.OrderRepositoryImpl
import com.vasilyev.crocostesttask.domain.model.order.Order
import com.vasilyev.crocostesttask.domain.model.route.EndLocationModel
import com.vasilyev.crocostesttask.domain.model.route.StartLocationModel
import com.vasilyev.crocostesttask.domain.usecase.GetOrderByIdUseCase
import com.vasilyev.crocostesttask.domain.usecase.GetRouteUseCase
import kotlinx.coroutines.launch

class TrackOrderViewModel: ViewModel() {
    private val mapRepository = MapRepositoryImpl
    private val orderRepository = OrderRepositoryImpl

    private val getRouteUseCase = GetRouteUseCase(mapRepository)
    private val getOrderByIdUseCase = GetOrderByIdUseCase(orderRepository)

    private val _order: MutableLiveData<Order> = MutableLiveData()
    val order: LiveData<Order>
        get() = _order

    private val _route: MutableLiveData<List<LatLng>> = MutableLiveData()
    val route: LiveData<List<LatLng>>
        get() = _route

    private val _startLocation: MutableLiveData<StartLocationModel> = MutableLiveData()
    val startLocation: LiveData<StartLocationModel>
        get() = _startLocation

    private val _endLocation: MutableLiveData<EndLocationModel> = MutableLiveData()
    val endLocation: LiveData<EndLocationModel>
        get() = _endLocation


    fun getRoute(order: Order){
        viewModelScope.launch {
            val route = getRouteUseCase.invoke(order.restaurantAddress, order.deliveryAddress)

            _route.postValue(decodePoly(route.route))
            _startLocation.postValue(route.startLocation)
            _endLocation.postValue(route.endLocation)
        }
    }

    fun getOrder(orderId: Long){
        _order.value = getOrderByIdUseCase.invoke(orderId)
    }


    //decoding polylines from string to list of latlng coordinates
    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5,
                lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }
}