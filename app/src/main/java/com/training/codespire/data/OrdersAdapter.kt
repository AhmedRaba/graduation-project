package com.training.codespire.data

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.training.codespire.R
import com.training.codespire.databinding.ItemProductOrderBinding
import com.training.codespire.network.my_orders.MyOrdersResponseItem

class OrdersAdapter(
    private var orders: List<MyOrdersResponseItem>,
    private val onItemClick: (Int) -> Unit,
    private val onDownloadClick: (MyOrdersResponseItem) -> Unit
) : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    inner class OrdersViewHolder(val binding: ItemProductOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val orderId = orders[position].id
                    onItemClick(orderId)
                }
            }
            binding.btnDownloadOrder.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val order = orders[position]
                    onDownloadClick(order)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val binding = ItemProductOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val order = orders[position]
        with(holder.binding) {
            tvProductName.text = order.name
            tvProductPrice.text = "$${order.price}"

            loadingAnimation.visibility = View.VISIBLE
            loadingAnimation.playAnimation()

            ivProduct.setImageResource(R.drawable.white_background)

            order.image?.let {
                Glide.with(root.context)
                    .load(it)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.iv_dev_store_logo)
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            loadingAnimation.visibility = View.GONE
                            loadingAnimation.cancelAnimation()
                            ivProduct.setImageDrawable(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // Optionally handle what to do if the load is cancelled
                        }
                    })
            }
        }
    }

    fun updateOrders(newOrders: List<MyOrdersResponseItem>) {
        orders = newOrders
        notifyDataSetChanged()
    }
}
