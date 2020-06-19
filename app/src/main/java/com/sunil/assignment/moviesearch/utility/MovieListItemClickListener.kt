package com.sunil.assignment.moviesearch.utility

import com.sunil.assignment.moviesearch.model.Movie
import java.text.FieldPosition

interface MovieListItemClickListener {
  fun onItemClick(item:Movie,position: Int)
}