package com.vasilyev.crocostesttask.presentation.track

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.vasilyev.crocostesttask.R
import com.vasilyev.crocostesttask.databinding.ActivityTrackOrderBinding
import com.vasilyev.crocostesttask.domain.model.order.Order
import com.vasilyev.crocostesttask.domain.model.order.OrderStatus
import com.vasilyev.crocostesttask.presentation.BaseActivity

class TrackOrderActivity : BaseActivity(), OnMapReadyCallback{
    private var _binding: ActivityTrackOrderBinding? = null
    private val binding: ActivityTrackOrderBinding
        get() = _binding ?: throw RuntimeException("ActivityTrackOrderBinding is null")

    private val viewModel: TrackOrderViewModel by viewModels()

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTrackOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActivitySettings()
        parseIntent()
        initMap()
        observeViewModel()
    }

    private fun parseIntent(){
        if(!intent.hasExtra(EXTRA_ORDER_ID)) throw RuntimeException("EXTRA_ORDER_ID is absent")

        val orderId = intent.getLongExtra(EXTRA_ORDER_ID, Order.UNDEFINED_ID)
        getOrderById(orderId)
    }

    private fun getOrderById(orderId: Long) {
        viewModel.getOrder(orderId)
    }

    private fun initMap(){
        val mapFragment = SupportMapFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.map, mapFragment)
            .commit()

        mapFragment.getMapAsync(this)
    }

    private fun observeViewModel(){
        viewModel.order.observe(this){ order ->
            viewModel.getRoute(order)
            fillBottomSheet(order)
        }

        viewModel.route.observe(this){ route ->
            displayRoute(route)
            createCameraBounds(route)
        }

        viewModel.startLocation.observe(this){ startLocation ->
            setStartMarker(startLocation.lat, startLocation.lng)
        }

        viewModel.endLocation.observe(this){ endLocation ->
            setEndMarker(endLocation.lat, endLocation.lng)
        }
    }

    private fun fillBottomSheet(order: Order){
        with(binding){
            tvDeliveredAt.text = order.deliveryTime
            setOrderStatus(order)
        }
    }

    private fun setOrderStatus(order: Order){
        when (order.orderStatus){
            OrderStatus.ORDERED -> setStatusOrdered(order)
            OrderStatus.PACKED -> setStatusPacked(order)
            OrderStatus.DELIVERED -> setStatusDelivered(order)
        }
    }

    private fun setStatusOrdered(order: Order){
        with(binding){
            ivFirstStep.setImageDrawable(
                ContextCompat.getDrawable(this@TrackOrderActivity, R.drawable.passed_step))
            tvStatusOrderedTime.text = order.orderedTime
        }
    }

    private fun setStatusPacked(order: Order){
        setStatusOrdered(order)

        with(binding){
            ivSecondStep.setImageDrawable(
                ContextCompat.getDrawable(this@TrackOrderActivity, R.drawable.passed_step))
            lineFirstStep.setBackgroundColor(getColor(R.color.passed_step))
            tvStatusPackedTime.text = order.packedTime
        }
    }

    private fun setStatusDelivered(order: Order){
        setStatusPacked(order)
        with(binding){
            ivThirdStep .setImageDrawable(
                ContextCompat.getDrawable(this@TrackOrderActivity, R.drawable.passed_step))
            tvStatusDeliveredAt.text = order.deliveredTime
            lineSecondStep.setBackgroundColor(getColor(R.color.passed_step))
        }
    }

    private fun setStartMarker(lat: Double, lng: Double){
        val point = LatLng(lat, lng)

        googleMap.addMarker(
            MarkerOptions()
                .position(point)
                .icon(getStartMarkerIcon())
                .anchor(MARKER_ANCHOR_VALUE, MARKER_ANCHOR_VALUE)
        )
    }

    private fun setEndMarker(lat: Double, lng: Double){
        val point = LatLng(lat, lng)

        googleMap.addMarker(
            MarkerOptions()
                .position(point)
        )
    }


    private fun getStartMarkerIcon(): BitmapDescriptor{
        val bitmapMarker = ContextCompat.getDrawable(this, R.drawable.restaraunt_marker)
            ?.toBitmap() ?: throw java.lang.RuntimeException("Unknown resource id")

        return BitmapDescriptorFactory.fromBitmap(bitmapMarker)
    }

    private fun displayRoute(route: List<LatLng>){
        val options = createPolyLine(route)
        googleMap.addPolyline(options)
    }

    private fun createPolyLine(route: List<LatLng>): PolylineOptions {
        val polyOptions = PolylineOptions()
        polyOptions.color(getColor(R.color.dark_red)) // color of the route

        for(point in route){
            polyOptions.add(point)
        }

        return polyOptions
    }

    private fun createCameraBounds(route: List<LatLng>){
        val boundsBuilder = LatLngBounds.builder()

        for (point in route) {
            boundsBuilder.include(point)
        }

        val routeBounds = boundsBuilder.build()

        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(routeBounds, CAMERA_PADDING)
        googleMap.animateCamera(cameraUpdate)
    }

    private fun setActivitySettings(){
        val actionBarTitle = ContextCompat.getString(this, R.string.title_order_tracking)
        createCustomActionBar(actionBarTitle)
        enableActionHomeButton(true)
    }

    private fun centerCameraOnCity(){
        val cameraPosition = CameraPosition.Builder()
            .target(cityCoordinates)
            .zoom(cityZoom)
            .build()

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        centerCameraOnCity()
    }

    companion object{
        private const val EXTRA_ORDER_ID = "order_id"
        private const val CAMERA_PADDING = 150
        private val cityCoordinates = LatLng(51.16, 71.42) // ASTANA
        private const val cityZoom = 12f
        private const val MARKER_ANCHOR_VALUE = 0.5f

        fun getTrackOrderIntent(context: Context, orderId: Long): Intent{
            val intent = Intent(context, TrackOrderActivity::class.java).apply{
                putExtra(EXTRA_ORDER_ID, orderId)
            }

            return intent
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}