package com.vasilyev.crocostesttask.presentation.myorders.adapter.list

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.vasilyev.crocostesttask.R
import com.vasilyev.crocostesttask.domain.model.order.Order

class MyOrdersListAdapter : ListAdapter<Order, MyOrderListViewHolder>(OrdersListDiffUtilCallback()) {
    var onOrderClickCallback: ((order: Order) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.order_item, parent, false)

        return MyOrderListViewHolder(view)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: MyOrderListViewHolder, position: Int) {
        val order = currentList[position]
        val context = holder.itemView.context

        with(holder){
            orderTitle.text = order.orderTitle

            orderId.text = spanOrderId(context, order.orderId)

            orderAmountPaid.text = spanAmountPaid(context, order.total)

            orderDeliveredAt
                .text = "${ContextCompat.getString(context, R.string.card_to_be_delivered)} ${order.deliveryTime}"

            orderPreview.setImageDrawable(ContextCompat.getDrawable(context, order.orderPreviewImage))
        }

        holder.itemView.setOnClickListener {
            onOrderClickCallback?.invoke(order)
        }
    }

    private fun spanOrderId(context: Context, orderId: Long): Spannable{
        val spanColor = ContextCompat.getColor(context, R.color.black)
        val orderIdTitle = ContextCompat.getString(context, R.string.card_order_id)

        val spannableOrderId = SpannableStringBuilder("$orderIdTitle $orderId")
        spannableOrderId.setSpan(
            ForegroundColorSpan(spanColor),
            orderIdTitle.length,
            spannableOrderId.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return spannableOrderId
    }

    private fun spanAmountPaid(context: Context, amountPaid: Int): Spannable{
        val spanColor = ContextCompat.getColor(context, R.color.black)
        val amountPaidTitle = ContextCompat.getString(context, R.string.card_amount_paid)

        val spannableAmountPaid = SpannableStringBuilder("$amountPaidTitle $amountPaid$CURRENCY_SIGN")

        spannableAmountPaid.setSpan(
            ForegroundColorSpan(spanColor),
            amountPaidTitle.length,
            spannableAmountPaid.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return spannableAmountPaid
    }

    companion object{
        private const val CURRENCY_SIGN = '₸'
    }
}