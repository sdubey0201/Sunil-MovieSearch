package com.sunil.assignment.moviesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sunil.assignment.moviesearch.R
import com.sunil.assignment.moviesearch.model.MovieDetailResponse
import com.sunil.assignment.moviesearch.utility.getProgressDrawable
import com.sunil.assignment.moviesearch.utility.loadImage
import com.sunil.assignment.moviesearch.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupActionBar()
        val mId:String = intent.getStringExtra("ID")
        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        viewModel.getMovieDetailById(mId)
        observeViewModel();

    }
    fun setupActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = getString(R.string.detail_activity_title);
    }


    private fun updateViews(response: MovieDetailResponse) {
        header_image.loadImage(
            response.poster, getProgressDrawable(this)
        )
        movieDescTitle.text = response.title
        movieDescSubTitle.text = response.year
        tv_genre.text = response.genre
        tv_duration.text = response.runtime
        tv_rating.text = response.imdbRating
        tv_synopsis.text = response.plot
        tv_score.text = response.metascore
        tv_reviews.text = response.imdbRating
        tv_popularity.text = response.imdbVotes
        tv_director.text = response.director
        tv_writer.text = response.writer
        tv_actor.text = response.actors
    }

    private fun observeViewModel() {
        viewModel.movieDetail.observe(this, Observer { movieDetailResponse ->
            movieDetailResponse?.let {
                updateViews(it)
            }
        })
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