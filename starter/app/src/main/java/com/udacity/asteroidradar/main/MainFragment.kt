package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.NasaAPI
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.persistence.AsteroidsDatabase
import com.udacity.asteroidradar.persistence.asDAOModel
import com.udacity.asteroidradar.persistence.getAsteroidsDataBase
import kotlinx.coroutines.*
import org.json.JSONObject

@RequiresApi(Build.VERSION_CODES.N)
class MainFragment : Fragment() {

    lateinit var pictureOfDay : PictureOfDay

    var count: Int = 0

    lateinit var db : AsteroidsDatabase


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setRecyclerViewConfiguration(binding)
        updateAllListenerBasedOnViewModel()

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
        recyclerView = binding.asteroidRecycler
        recyclerView.adapter = MainAsteroidAdapter(MutableLiveData(emptyList()), requireContext())
    }

    private fun updateAllListenerBasedOnViewModel(){
        viewModel.listOfAsteroids.observe(viewLifecycleOwner) {
            (recyclerView.adapter as MainAsteroidAdapter).insertAsteroids(it)
        }
    }

}
