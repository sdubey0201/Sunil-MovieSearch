package com.sunil.assignment.moviesearch.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import com.sunil.assignment.moviesearch.R
import com.sunil.assignment.moviesearch.view.adapter.MovieListAdapter
import com.sunil.assignment.moviesearch.viewmodel.MovieSearchListViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sunil.assignment.moviesearch.model.Movie
import com.sunil.assignment.moviesearch.utility.GridItemDecoration
import com.sunil.assignment.moviesearch.utility.MovieListItemClickListener
import kotlinx.android.synthetic.main.activity_movie_search_listview.*

class MovieSearchListActivity : AppCompatActivity() , MovieListItemClickListener {

    private lateinit var viewModel: MovieSearchListViewModel
    private val movieListAdapter = MovieListAdapter(arrayListOf(),this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search_listview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        viewModel = ViewModelProviders.of(this).get(MovieSearchListViewModel::class.java)
        viewModel.getMovies("Marvel", 1)
        addLayoutManager()
        initializeViews()
        observeDataChanges();
    }

    fun initializeViews() {
        movieSearchView.setIconifiedByDefault(false)
        movieSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getMovies(it, 1)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
    }

    fun observeDataChanges() {
        viewModel.movies.observe(this, Observer { movies ->
            movies?.let {
                movieRecyclerView.visibility = View.VISIBLE
                movieListAdapter.updateMovieList(movies)
                Log.d("MovieSearchActivity ","item size ${movies.size}")

            }
        })
        viewModel.isNetworkError.observe(this, Observer { isNetworkErorr ->
            isNetworkErorr?.let {
                errorTextView.visibility = if (it) View.GONE else View.VISIBLE
                Log.d("MovieSearchActivity ","isNetworkErorr ${isNetworkErorr}")
            }
        })

        viewModel.isNetworkCallInProgress.observe(this, Observer { isNetworkCallInProggress->
            isNetworkCallInProggress?.let {
               listProgressBar.visibility= if (isNetworkCallInProggress) View.GONE else View.GONE
                Log.d("MovieSearchActivity ","isNetworkCallInProggress ${isNetworkCallInProggress}")


            }
        })

    }

    fun addLayoutManager(){
        movieRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridItemDecoration(20, 2))
            adapter = movieListAdapter
        }

    }

    override fun onItemClick(item: Movie, position: Int) {
        val intent = Intent(this@MovieSearchListActivity,MovieDetailActivity::class.java);
        intent.putExtra("ID", item.imdbID)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}