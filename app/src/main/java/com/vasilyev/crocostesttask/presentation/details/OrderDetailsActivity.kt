package com.vasilyev.crocostesttask.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.vasilyev.crocostesttask.R
import com.vasilyev.crocostesttask.databinding.ActivityOrderDetailsBinding
import com.vasilyev.crocostesttask.domain.model.order.Order
import com.vasilyev.crocostesttask.presentation.BaseActivity
import com.vasilyev.crocostesttask.presentation.track.TrackOrderActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDetailsActivity : BaseActivity() {
    private var _binding: ActivityOrderDetailsBinding? = null
    private val binding: ActivityOrderDetailsBinding
        get() = _binding ?: throw RuntimeException("ActivityOrderDetailsBinding is null")

    private lateinit var order: Order

    private val viewModel: OrderDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActivitySettings()
        setListeners()
        parseIntent()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.order.observe(this){ order ->
            this.order = order
            displayOrder(order)
        }
    }

    private fun displayOrder(order: Order){
        with(binding){
            tvOrderId.text = spanOrderId(order.orderId)
            tvOrderDate.text = spanOrderDate(order.orderDate)
            tvOrderTitle.text = order.orderTitle
            ivOrderPreview.setImageDrawable(
                ContextCompat.getDrawable(this@OrderDetailsActivity, order.orderPreviewImage)
            )
            tvOrderCount.text = getOrderCountText(order.count)
            tvOrderSubTotal.text = getOrderSubTotalText(order.subTotal)
            tvDeliveredAt.text = spanOrderDeliveryTime(order.deliveryTime)
            tvDeliveryAddress.text= order.deliveryAddress
            tvSubTotal.text = getOrderSubTotalText(order.subTotal)
            tvTaxes.text = getOrderTaxesText(order.taxes)
            tvDeliveryFee.text = getOrderDeliveryFeeText(order.deliveryFee)
            tvTotal.text = getOrderTotalText(order.total)
        }
    }

    private fun getOrderSubTotalText(subTotal: Int): String{
        return "$subTotal$CURRENCY_SIGN"
    }

    private fun getOrderTaxesText(taxes: Int): String{
        return "$taxes$CURRENCY_SIGN"
    }

    private fun getOrderDeliveryFeeText(deliveryFee: Int): String{
        return "$deliveryFee$CURRENCY_SIGN"
    }

    private fun getOrderTotalText(total: Int): String{
        return "$total$CURRENCY_SIGN"
    }
    private fun getOrderCountText(orderCount: Int): String{
        val orderCountTitle = ContextCompat.getString(this, R.string.details_order_count)

        return "$orderCountTitle $orderCount"
    }

    private fun spanOrderDeliveryTime(orderDeliveryTime: String): Spannable{
        val orderDeliveryTimeTitle = ContextCompat.getString(this,
            R.string.details_order_delivery_time)

        val spannableDeliveryTime =
            SpannableStringBuilder("$orderDeliveryTimeTitle $orderDeliveryTime")


        spannableDeliveryTime.setSpan(
            AbsoluteSizeSpan(ORDER_DELIVERY_TIME_TEXT_SIZE, true),

            orderDeliveryTimeTitle.length,
            spannableDeliveryTime.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return spannableDeliveryTime
    }

    private fun spanOrderId(orderId: Long): Spannable {
        val spanColor = ContextCompat.getColor(this, R.color.black)
        val orderIdTitle = ContextCompat.getString(this, R.string.card_order_id)

        val spannableOrderId = SpannableStringBuilder("$orderIdTitle $orderId")
        spannableOrderId.setSpan(
            ForegroundColorSpan(spanColor),
            orderIdTitle.length,
            spannableOrderId.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return spannableOrderId
    }

    private fun spanOrderDate(orderDate: Long): Spannable {
        val spanColor = ContextCompat.getColor(this, R.color.black)
        val orderDateTitle = ContextCompat.getString(this, R.string.details_order_date)

        val spannableDate = SpannableStringBuilder(
            "$orderDateTitle ${timestampToDateString(orderDate)}"
        )

        spannableDate.setSpan(
            ForegroundColorSpan(spanColor),
            orderDateTitle.length,
            spannableDate.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return spannableDate
    }



    private fun timestampToDateString(timestamp: Long): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTER, Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

    private fun parseIntent(){
        if(!intent.hasExtra(EXTRA_ORDER_ID)) throw RuntimeException("EXTRA_ORDER_ID is absent")

        val orderId = intent.getLongExtra(EXTRA_ORDER_ID, Order.UNDEFINED_ID)
        getOrderById(orderId)
    }

    private fun getOrderById(orderId: Long){
        viewModel.getOrderById(orderId)
    }


    private fun setListeners() {
        binding.btnTrackOrder.setOnClickListener {
            val intent = TrackOrderActivity.getTrackOrderIntent(this, order.orderId)
            startActivity(intent)
        }
    }

    private fun setActivitySettings(){
        val actionBarTitle = ContextCompat.getString(this, R.string.title_order_details)
        createCustomActionBar(actionBarTitle)
        enableActionHomeButton(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    companion object{
        private const val EXTRA_ORDER_ID = "order_id"
        private const val DATE_FORMAT_PATTER = "dd/MM/yyyy"
        private const val ORDER_DELIVERY_TIME_TEXT_SIZE = 18
        private const val CURRENCY_SIGN = '₸'

        fun getDetailsIntent(context: Context, orderId: Long): Intent{
            val intent = Intent(context, OrderDetailsActivity::class.java).also { intent ->
                intent.putExtra(EXTRA_ORDER_ID, orderId)
            }

            return intent
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}