package com.training.codespire.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.training.codespire.databinding.ItemProductBinding
import com.training.codespire.network.my_orders.MyOrdersResponseItem

class OrdersAdapter(
    private val orders: List<MyOrdersResponseItem>,
    private val onItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    inner class OrdersViewHolder(val binding: ItemProductBinding) : ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val productId = orders[position].id
                    onItemClick(productId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        }
    }


}