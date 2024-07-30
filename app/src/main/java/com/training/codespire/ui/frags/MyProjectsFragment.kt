package com.training.codespire.ui.frags

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.training.codespire.R
import com.training.codespire.data.OrdersAdapter
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentMyProjectsBinding
import com.training.codespire.network.my_orders.MyOrdersResponseItem

class MyProjectsFragment : Fragment() {

    private lateinit var binding: FragmentMyProjectsBinding
    private lateinit var authViewmodel: AuthViewmodel
    private lateinit var ordersAdapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyProjectsBinding.inflate(inflater, container, false)
        authViewmodel = ViewModelProvider(this)[AuthViewmodel::class.java]

        binding.ivEmptyBox.visibility = View.GONE

        ordersAdapter = OrdersAdapter(emptyList(), { productId ->
            navigateToProductDetails(productId)
        }, { order ->
            downloadOrder(order)
        })

        binding.recyclerViewMyOrders.adapter = ordersAdapter

        authViewmodel.ordersLiveData.observe(viewLifecycleOwner) { orders ->
            hideLoading()
            if (orders.isNullOrEmpty()) {
                binding.ivEmptyBox.visibility = View.VISIBLE
            } else {
                binding.ivEmptyBox.visibility = View.GONE
                ordersAdapter.updateOrders(orders)
            }
        }

        authViewmodel.errorLiveData.observe(viewLifecycleOwner) { error ->
            hideLoading()
            showToast(error.toString())
        }

        fetchOrders()

        return binding.root
    }

    private fun fetchOrders() {
        showLoading()
        authViewmodel.fetchOrders()
    }

    private fun navigateToProductDetails(productId: Int) {
        val action =
            MyProjectsFragmentDirections.actionMyProjectsFragmentToProductDetailsFragment(productId)
        findNavController().navigate(action)
    }

    private fun downloadOrder(order: MyOrdersResponseItem) {
        val request = DownloadManager.Request(Uri.parse(order.downloadUrl))
            .setTitle(order.name)
            .setDescription("Downloading file...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, order.name)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        showToast("Downloading ${order.name}")
    }

    private fun showLoading() {
        binding.myOrdersLayout.visibility = View.GONE
        binding.ivEmptyBox.visibility = View.GONE
        binding.progressBarOrders.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.myOrdersLayout.visibility = View.VISIBLE
        binding.progressBarOrders.visibility = View.GONE
    }


    private fun showToast(message: String) {

        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast_layout, null)
        val textView = layout.findViewById<TextView>(R.id.toast_text)
        textView.text = message


        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()

    }
}
