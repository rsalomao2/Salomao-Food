package com.salomao.presentation.placedetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.salomao.R
import com.salomao.data.pojo.Place
import com.salomao.databinding.FragmentPlaceDetailBinding
import com.salomao.domain.di.injectPlaceDetailKoin
import com.salomao.presentation.placedetail.adapter.PlaceMenuAdapter
import com.salomao.presentation.placelist.PlaceListFragment.Companion.ARGS_PLACE
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceDetailFragment : Fragment(), OnMapReadyCallback {

    private val viewModel by viewModel<PlaceDetailViewModel>()

    private val placeMenuAdapter by lazy {
        PlaceMenuAdapter(
            viewModel.onItemClick
        )
    }
    private lateinit var binding: FragmentPlaceDetailBinding
    private lateinit var mMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectPlaceDetailKoin()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_detail, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycleView()
        setMap()
        getPlace()?.let {
            binding.place = it
        }
    }

    private fun setCamera(position: LatLng) {
        val latLng = LatLng(position.latitude, position.longitude)

        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(17f)
            .bearing(10f)
            .build()
        mMap.setOnMapLoadedCallback {
            val newCameraPosition = CameraUpdateFactory.newCameraPosition(cameraPosition)
            mMap.animateCamera(newCameraPosition)
        }
    }

    private fun getPlace(): Place? {
        arguments?.let {
            return it.getSerializable(ARGS_PLACE) as Place
        }
        return null
    }

    private fun setMap() {
        val mapFrag = childFragmentManager.findFragmentById(R.id.map)
        val mapFragment = mapFrag as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getPlace()?.let {
            val position = LatLng(it.address.latitude, it.address.longitude)
            mMap.addMarker(MarkerOptions().position(position).title(it.name))
            setCamera(position)
        }
    }

    private fun setRecycleView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(true)
            adapter = placeMenuAdapter
        }
    }

}
