package com.sunil.assignment.moviesearch.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sunil.assignment.moviesearch.R
import com.sunil.assignment.moviesearch.model.Movie
import com.sunil.assignment.moviesearch.utility.GridItemDecoration
import com.sunil.assignment.moviesearch.utility.MovieListItemClickListener
import com.sunil.assignment.moviesearch.view.adapter.MovieListAdapter
import com.sunil.assignment.moviesearch.viewmodel.MovieSearchListViewModel
import kotlinx.android.synthetic.main.activity_movie_search_listview.*

class MovieSearchListActivity : AppCompatActivity() , MovieListItemClickListener {

    private lateinit var viewModel: MovieSearchListViewModel
    private val movieListAdapter = MovieListAdapter(arrayListOf(),this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search_listview)
        setupActionBar()
        viewModel = ViewModelProviders.of(this).get(MovieSearchListViewModel::class.java)
        viewModel.getMovies("Marvel", 1)
        addLayoutManager()
        initializeViews()
        observeDataChanges();
    }

    fun setupActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = getString(R.string.search_activity_title);
    }

    private fun initializeViews() {
    //    movieSearchView.setIconifiedByDefault(false)
//        movieSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let { it: String ->
//                    viewModel.getMovies(it, 1)
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(query: String?): Boolean {
//                return true
//            }
//        })
    }

    private fun observeDataChanges() {
        viewModel.movies.observe(this, Observer { movies ->
            movies?.let {
                movieRecyclerView.visibility = View.VISIBLE
                movieListAdapter.updateMovieList(movies)
                Log.d("MovieSearchActivity ","item size ${movies.size}")

            }
        })
        viewModel.isNetworkError.observe(this, Observer { isNetworkErorr ->
            isNetworkErorr?.let {
                errorTextView.visibility = if (it) View.VISIBLE else View.GONE
                Log.d("MovieSearchActivity ","isNetworkErorr ${isNetworkErorr}")
            }
        })

        viewModel.isNetworkCallInProgress.observe(this, Observer { isNetworkCallInProggress->
            isNetworkCallInProggress?.let {
               listProgressBar.visibility= if (isNetworkCallInProggress) View.VISIBLE else View.GONE
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
        Log.d("***SearchActivity**","$position")
        Log.d("***SearchActivity**","${item.title}")
        val intent = Intent(this@MovieSearchListActivity,MovieDetailActivity::class.java);
        intent.putExtra("ID", item.imdbID)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                Log.d("***SearchActivity**","press back button")
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_activity_menu,menu)
        val  searchItem = menu?.findItem(R.id.searchView)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { it: String ->
                    viewModel.getMovies(it, 1)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
              return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}