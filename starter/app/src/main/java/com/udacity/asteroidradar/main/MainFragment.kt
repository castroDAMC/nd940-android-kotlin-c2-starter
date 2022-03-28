package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.getNextSevenDays
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.databinding.FragmentMainBinding

@RequiresApi(Build.VERSION_CODES.N)
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setRecyclerViewConfiguration()
        updateAllListenersBasedOnViewModel()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val dateList = getNextSevenDays()

        (binding.asteroidRecycler.adapter as MainAsteroidAdapter).insertAsteroids(
            viewModel.listOfAsteroids.value!!.filter {
                when (item.itemId) {
                    R.id.show_today_asteroids -> it.closeApproachDate == getToday()
                    R.id.show_week_asteroids -> dateList.contains(it.closeApproachDate)
                    else -> true //Only happens when item.itemId is R.id.show_saved_asteroids
                }
            }
        )
        return true
    }


    private fun setRecyclerViewConfiguration() {
        binding.asteroidRecycler.adapter =
            MainAsteroidAdapter(
                MutableLiveData(emptyList()),
                {setClickItemFunction(it)}
            )
    }

    private fun updateAllListenersBasedOnViewModel() {
        viewModel.listOfAsteroids.observe(viewLifecycleOwner) {
            (binding.asteroidRecycler.adapter as MainAsteroidAdapter).insertAsteroids(it)
            binding.statusLoadingWheel.visibility =
                when (viewModel.listOfAsteroids.value.isNullOrEmpty()) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
        }
    }

    private fun setClickItemFunction(asteroid: Asteroid) {
        findNavController()
            .navigate(MainFragmentDirections.actionShowDetail(asteroid))
    }

}
