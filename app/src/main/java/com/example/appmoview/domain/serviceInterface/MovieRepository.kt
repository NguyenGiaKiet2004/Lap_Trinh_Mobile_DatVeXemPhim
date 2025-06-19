package com.example.appmoview.domain.serviceInterface

import androidx.lifecycle.LiveData
import com.example.appmoview.domain.model.BookingRequest
import com.example.appmoview.domain.model.MovieDetail
import com.example.appmoview.domain.model.MovieRequest
import com.example.appmoview.domain.model.Showtime

interface MovieRepository {
    fun fetchAllMovies()
    fun fetchMovieById(id: Int, onResult: (MovieDetail?) -> Unit)
    val movies: LiveData<List<MovieRequest>>
    val isLoading: LiveData<Boolean>
    val errorMessage: LiveData<String>

    val showtimeList: LiveData<List<Showtime>>
    fun fetchAllShowtime()

    fun checkBookedSeats(
        showtimeId: Int,
        seatIds: List<Int>,
        onResult: (List<Int>?) -> Unit
    )

    fun createBooking(
        bookingRequest: BookingRequest,
        onResult: (Boolean, String) -> Unit
    )
}
