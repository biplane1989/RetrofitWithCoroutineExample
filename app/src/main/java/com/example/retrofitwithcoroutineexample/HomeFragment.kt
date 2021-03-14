package com.example.actionlistenerexample.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.holidayimage.`object`.ImageItem
import com.example.retrofitwithcoroutineexample.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    val TAG = "giangtd"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewmodel: HomeViewmodel by viewModels()


    private val observerListData = Observer<List<ImageItem>?> {
        it?.let {
            it.forEach {
                Log.d(TAG, "observerListData: $it")
            }
        }
    }

    private val observerLoading = Observer<Boolean> {
        if (!it) {
            binding.loading.visibility = View.GONE
        } else {
            binding.loading.visibility = View.VISIBLE
        }
    }

    private val observerExceptionNetwork = Observer<ExceptionNetwork> { excepton ->
        excepton?.let {
            when (it) {
                ExceptionNetwork.SERVER_NOT_REACH -> {
                    Log.d(TAG, ": SERVER_NOT_REACH")
                }
                ExceptionNetwork.NOT_CONNECTED -> {
                    Log.d(TAG, ": NOT_CONNECTED")
                }
                ExceptionNetwork.DATA_NULL -> {
                    Log.d(TAG, ": DATA_NULL")
                }
            }
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewmodel = homeViewmodel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewmodel.networkData.observe(viewLifecycleOwner, observerListData)
        homeViewmodel.exceptionNetWork.observe(viewLifecycleOwner, observerExceptionNetwork)
        homeViewmodel.loading.observe(viewLifecycleOwner, observerLoading)
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}