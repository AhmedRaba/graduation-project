package com.training.codespire.ui.frags.product_details

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.training.codespire.data.ReviewProductAdapter
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentProductDetailsBinding

private const val TAG = "ProductDetailsFragment"

class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var authViewModel: AuthViewmodel
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(this)[AuthViewmodel::class.java]
        sharedPreferencesUtil = SharedPreferencesUtil(requireContext())

        val productId = args.productId
        authViewModel.getProductDetails(productId)

        setupObservers()


        binding.icBackArrow.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun setupObservers() {
        authViewModel.productDetailsLiveData.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.tvDetailsProductName.text = it.name
                binding.tvDetailsProductDesc.text = it.description
                binding.tvDetailsProductUploader.text = "(${it.uploadedBy})"
                Glide.with(this).load(it.images[0].url).into(binding.ivDetailsProductImage)

                binding.tvNumReviews.text = "(${it.reviewsCount})"
                val reviewAdapter = ReviewProductAdapter(it.reviews)
                binding.reviewsRecyclerView.adapter = reviewAdapter

                setupPayment(it.downloadUrl, it.name, it.canBuy)
                setupSubmitReviewButton(args.productId,it.canReview)

                binding.tvTotalPrice.text = "Total Price: $" + it.price
            }
        }

        authViewModel.reviewLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                refreshFragment()
            }
        }

        authViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupSubmitReviewButton(productId: Int,canReview:Boolean) {
        if (!canReview){
            binding.reviewLayout.visibility=View.GONE
        }else{
            binding.reviewLayout.visibility=View.VISIBLE
            binding.btnSubmitReview.setOnClickListener {
                val rating = binding.ratingBarSubmitReview.rating.toInt()
                val comment = binding.etReview.text.toString()
                if (rating > 0 && comment.isNotEmpty()) {
                    authViewModel.submitReview(productId, rating, comment)
                } else {
                    Toast.makeText(requireContext(), "Please provide a rating and comment", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    private fun setupPayment(url: String, fileName: String, canBuy: Boolean) {
        if (!canBuy) {
            binding.btnBuyNow.text = "Download"
            binding.btnBuyNow.setOnClickListener {
                downloadFile(url, fileName)
            }
        } else {
            binding.btnBuyNow.setOnClickListener {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToPaymentFragment(args.productId)
                findNavController().navigate(action)
            }
        }
    }

    private fun downloadFile(url: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading file...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Snackbar.make(binding.root, "File downloaded successfully", Snackbar.LENGTH_SHORT).show()
    }


    private fun refreshFragment() {
        findNavController().navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentSelf(args.productId))
    }
}
