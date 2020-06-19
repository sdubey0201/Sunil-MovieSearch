package com.sunil.assignment.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunil.assignment.moviesearch.model.MovieDetailResponse
import com.sunil.assignment.moviesearch.repository.MovieApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel : ViewModel() {
    private val movieApiService = MovieApiService()
    private val compositeDisposable = CompositeDisposable()

    val movieDetail = MutableLiveData<MovieDetailResponse>()
    val isNetworkError = MutableLiveData<Boolean>();
    val isNetworkCallInProgress = MutableLiveData<Boolean>();

    fun getMovieDetailById(mId:String) {
        compositeDisposable.add(movieApiService.getMovieDetail(mId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isNetworkCallInProgress.value = true }
            .subscribe({ result ->
                movieDetail.value = result;
                isNetworkError.value=false;
                isNetworkCallInProgress.value=false
            }, { error ->
                error.printStackTrace();
                isNetworkError.value=true;
                isNetworkCallInProgress.value=false
            } )

        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}