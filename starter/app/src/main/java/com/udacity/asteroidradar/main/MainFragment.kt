package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.NasaAPI
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.persistence.AsteroidsDataBaseEntity
import com.udacity.asteroidradar.persistence.AsteroidsDatabase
import com.udacity.asteroidradar.persistence.asDAOModel
import com.udacity.asteroidradar.persistence.getAsteroidsDataBase
import kotlinx.coroutines.*
import org.json.JSONObject

class MainFragment : Fragment() {

    lateinit var pictureOfDay : PictureOfDay
    lateinit var asteroids: ArrayList<Asteroid>
    val scope = CoroutineScope(Dispatchers.IO )

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

        networkRequest()
//        setRecyclerViewConfiguration(binding)

        setHasOptionsMenu(true)
        binding.activityMainImageOfTheDay.setOnClickListener { clickImage(it) }


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun networkRequest() {

        scope.launch {
            pictureOfDay = NasaAPI.retrofitService.getImageOfTheDay(Constants.API_KEY)
            asteroids = parseAsteroidsJsonResult(JSONObject(NasaAPI.retrofitService.getAsteroidsState(Constants.API_KEY)))

            db = getAsteroidsDataBase(requireContext())

            db.userDao().insertAsteroidsInBatch(*asteroids.asDAOModel().toTypedArray())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun clickImage(view: View){
        scope.launch {
            Log.d("TESTE_TESTE",db.userDao().getAsteroidById(asteroids[count].id.toString()).codename)
            count = count + 1
        }
    }

    private fun doSomething(){

    }

    private fun setRecyclerViewConfiguration(binding: FragmentMainBinding){
//        recyclerView = binding.asteroidRecycler
        recyclerView.adapter = context?.let { MainAsteroidAdapter(MutableLiveData(), it) }
    }

}
