package com.sunil.assignment.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunil.assignment.moviesearch.model.Movie
import com.sunil.assignment.moviesearch.model.MovieSearchResponse
import com.sunil.assignment.moviesearch.repository.MovieApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieSearchListViewModel : ViewModel() {
    private val disposables = CompositeDisposable()
    val movies = MutableLiveData<List<Movie>>()
    val isNetworkError = MutableLiveData<Boolean>();
    val isNetworkCallInProgress = MutableLiveData<Boolean>();
    private val movieApiService = MovieApiService()

    fun getMovies(movieTitle: String, pageNumber: Int) {
        isNetworkCallInProgress.value = true;
//        val myObservable = Observable.just("a", "b", "c", "d")
//        myObservable.doOnSubscribe { disposable: Disposable? ->
//            println(
//                "Subscribed!"
//            )
//        }
//            .doOnDispose { println("Disposing!") }
//            .subscribe { s: String? ->
//                println(
//                    s
//                )
//            }

        disposables.add(
            movieApiService.getMovies(movieTitle, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieSearchResponse>() {
                    override fun onSuccess(movieListResponse: MovieSearchResponse) {
                        movies.value = movieListResponse.movies
                        isNetworkError.value = false
                        isNetworkCallInProgress.value = false
                    }

                    override fun onError(e: Throwable) {
                        isNetworkError.value = true
                        isNetworkCallInProgress.value = false
                        e.printStackTrace()
                    }

                })


        )

    }
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}