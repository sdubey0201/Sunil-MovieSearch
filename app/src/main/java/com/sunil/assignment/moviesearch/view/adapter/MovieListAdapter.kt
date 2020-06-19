package com.sunil.assignment.moviesearch.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.sunil.assignment.moviesearch.R
import com.sunil.assignment.moviesearch.model.Movie
import com.sunil.assignment.moviesearch.utility.MovieListItemClickListener
import com.sunil.assignment.moviesearch.utility.getProgressDrawable
import com.sunil.assignment.moviesearch.utility.loadImage
import kotlinx.android.synthetic.main.movie_list_adapter_item.view.*

class MovieListAdapter(private val movies: ArrayList<Movie>,private val itemClickListener: MovieListItemClickListener) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){
    class MovieViewHolder(view: View) : ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_list_adapter_item, parent, false)
        return MovieViewHolder(
            view
        )
    }

    override fun getItemCount() = movies.size
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemView.movieItemTitle.text = movies[position].title
        setImage(holder,position);
       setClickListener(holder,position)
    }

    private fun setImage(viewHolder: ViewHolder, position: Int){
        viewHolder.itemView.movieItemImageView.loadImage(
            movies[position].poster,
            getProgressDrawable(viewHolder.itemView.context)
        )
    }

    private fun setClickListener(viewHolder: ViewHolder, position: Int){
        viewHolder.itemView.setOnClickListener{  Log.d("MovieListAdapter","click on movie item $position")
            itemClickListener?.onItemClick(movies.get(position),position);
        }

    }

    fun updateMovieList(updatedMovieList: List<Movie>) {
        movies.clear()
        movies.addAll(updatedMovieList)
        notifyDataSetChanged()
    }


}