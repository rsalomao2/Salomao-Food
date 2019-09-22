package com.salomao.presentation.placelist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salomao.R
import com.salomao.databinding.FragmentPlaceListBinding
import com.salomao.domain.di.injectPlaceListKoin
import com.salomao.domain.extention.hideKeyboard
import com.salomao.domain.extention.observeEventNotHandled
import com.salomao.domain.extention.observeIfNotNull
import com.salomao.domain.extention.toast
import com.salomao.domain.provider.DrawableProvider
import com.salomao.presentation.placelist.adapter.PlaceAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceListFragment : Fragment() {

    private val viewModel by viewModel<PlaceListViewModel>()
    private val drawableProvider by inject<DrawableProvider>()

    private val placeAdapter by lazy {
        PlaceAdapter(
            drawableProvider,
            viewModel.onItemClick
        )
    }
    companion object{
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        const val ARGS_PLACE = "argsPlace"
    }
    private lateinit var binding: FragmentPlaceListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectPlaceListKoin()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycleView()
        setListeners()
        setObservers()
        viewModel.loadPlaceFromGpsLocation()
    }

    private fun setListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.loadPlacesFromQuery(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {return true}

        })
    }

    private fun setObservers() {
        setOnNavToSecondObserver()
        setPlaceListObserver()
        setOnPlaceClickObserver()
        setErrorMessageObserver()
        setKeyBoardHideObserver()
        setGpsPermissionDeniedObserver()
    }

    private fun setGpsPermissionDeniedObserver() {
        viewModel.isGpsDenied.observeEventNotHandled(this) {
            getLocationPermission()
        }
    }

    private fun getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.loadPlaceFromGpsLocation()
                }
            }
        }
    }

    private fun setKeyBoardHideObserver() {
        viewModel.hideKeyBoard.observeIfNotNull(viewLifecycleOwner){
            hideKeyboard()
        }
    }

    private fun setErrorMessageObserver() {
        viewModel.errorMessage.observeEventNotHandled(viewLifecycleOwner){
            toast(it)
        }
    }

    private fun setOnPlaceClickObserver() {
        viewModel.currentItemClick.observeEventNotHandled(viewLifecycleOwner) {
            val bundle = bundleOf(Pair(ARGS_PLACE, it))
            findNavController().navigate(R.id.action_PlaceListFragment_to_placeDetailFragment,bundle)
        }
    }

    private fun setPlaceListObserver() {
        viewModel.placeList.observeEventNotHandled(viewLifecycleOwner) {
            placeAdapter.addItems(it)
        }

    }

    private fun setRecycleView() {
        binding.rvPlaces.apply {
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(true)
            adapter = placeAdapter
        }
    }

    private fun setOnNavToSecondObserver() {
        viewModel.navToSecond.observeEventNotHandled(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_PlaceListFragment_to_placeDetailFragment)
        }
    }

}
