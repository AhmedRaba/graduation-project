package com.training.codespire.ui.frags.product_details

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        sharedPreferencesUtil=SharedPreferencesUtil(requireContext())


        val productId = args.productId
        authViewModel.getProductDetails(productId)



        binding.icBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }


        authViewModel.productDetailsLiveData.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.tvDetailsProductName.text = it.name
                binding.tvDetailsProductDesc.text = it.description
                Glide.with(this).load(it.images[0].url).into(binding.ivDetailsProductImage)

                binding.tvNumReviews.text = "(${it.reviewsCount})"
                val reviewAdapter = ReviewProductAdapter(it.reviews)
                binding.reviewsRecyclerView.adapter = reviewAdapter


                customizeBuyButton(it.downloadUrl,it.name)



                binding.tvTotalPrice.text = "Total Price: $" + it.price
            }
        }



        return binding.root
    }


    private fun customizeBuyButton(url: String,fileName: String) {
        if (sharedPreferencesUtil.categoryId == 13) {
            binding.btnBuyNow.text = "Download"
            binding.btnBuyNow.setOnClickListener {
                downloadFile(url,fileName)
            }
        }
    }

    private fun downloadFile(url:String,fileName:String){
        val request=DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading file...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager=requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Snackbar.make(binding.root, "File downloaded successfully", Snackbar.LENGTH_SHORT).show()

    }


}
