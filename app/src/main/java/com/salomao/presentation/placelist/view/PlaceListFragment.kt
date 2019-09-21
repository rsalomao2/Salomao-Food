package com.salomao.presentation.placelist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.salomao.R
import com.salomao.databinding.FragmentPlaceListBinding
import com.salomao.domain.di.injectFirstKoin
import com.salomao.domain.extention.observeEventNotHandled
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceListFragment : Fragment() {

    private val viewModel by viewModel<PlaceListViewModel>()

    private lateinit var binding:FragmentPlaceListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectFirstKoin()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        setOnNavToSecond()
    }

    private fun setOnNavToSecond() {
        viewModel.navToSecond.observeEventNotHandled(viewLifecycleOwner){
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }

}
