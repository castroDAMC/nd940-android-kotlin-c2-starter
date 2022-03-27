package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

@RequiresApi(Build.VERSION_CODES.N)
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setRecyclerViewConfiguration(binding)
        updateAllListenersBasedOnViewModel(binding)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun setRecyclerViewConfiguration(binding: FragmentMainBinding){
        binding.asteroidRecycler.adapter = MainAsteroidAdapter(MutableLiveData(emptyList()))
    }

    private fun updateAllListenersBasedOnViewModel(binding: FragmentMainBinding){
        viewModel.listOfAsteroids.observe(viewLifecycleOwner) {
            (binding.asteroidRecycler.adapter as MainAsteroidAdapter).insertAsteroids(it)
            binding.statusLoadingWheel.visibility = when(viewModel.listOfAsteroids.value.isNullOrEmpty()){
                true -> View.VISIBLE
                false -> View.GONE
            }
        }
    }

}
