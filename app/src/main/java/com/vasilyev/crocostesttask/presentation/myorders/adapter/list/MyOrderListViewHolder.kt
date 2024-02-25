package com.vasilyev.crocostesttask.presentation.myorders.adapter.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vasilyev.crocostesttask.R

class MyOrderListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val orderTitle: TextView = itemView.findViewById(R.id.tv_order_name)
    val orderId: TextView = itemView.findViewById(R.id.tv_order_id)
    val orderAmountPaid: TextView = itemView.findViewById(R.id.tv_order_sub_total)
    val orderDeliveredAt: TextView = itemView.findViewById(R.id.tv_delivered_at)
    val orderPreview: ImageView = itemView.findViewById(R.id.iv_order_preview)
}