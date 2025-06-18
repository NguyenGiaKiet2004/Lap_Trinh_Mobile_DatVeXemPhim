package com.example.appmoview.data.source.api


import com.example.appmoview.domain.model.ApiResponse
import com.example.appmoview.domain.model.MovieDetail
import com.example.appmoview.domain.model.MovieRequest
import com.example.appmoview.domain.model.Showtime
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/get")
    fun getAllMovies(): Call<List<MovieRequest>>

    @GET("movie/get/{id}")
    fun getMovieById(@Path("id") id: Int): Call<ApiResponse<MovieDetail>>

    @GET("showtime/get") // hoặc đường dẫn tương ứng bạn có
    fun getAllShowtime(): Call<List<Showtime>>

    @POST("booking/check-booked-seats")
    fun checkBookedSeats(
        @Query("showtimeId") showtimeId: Int,
        @Body seatIds: List<Int>
    ): Call<List<Int>>


}
