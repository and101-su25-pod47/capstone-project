package com.example.capstoneproject_su25

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var movieList: MutableList<Movie>  // Change here!
    private lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        rvMovies = findViewById<RecyclerView>(R.id.movie_list)
        movieList = mutableListOf()
        fetchMoviePoster()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun fetchMoviePoster() {
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/movie/popular?api_key=264f7145d4edb035e04f09b6191e7263"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Headers?,
                json: JsonHttpResponseHandler.JSON
            ) {
                Log.d("Movie", "response successful: $json")

                try {
                    val resultsArray = json.jsonObject.getJSONArray("results")

                    for (i in 0 until resultsArray.length()) {
                        val movieObj = resultsArray.getJSONObject(i)
                        val title = movieObj.getString("title") // use "title" not "name"
                        val posterPath = movieObj.getString("poster_path")
                        val fullPosterUrl = "https://image.tmdb.org/t/p/w500$posterPath"

                        val newMovie = Movie(title, fullPosterUrl)
                        movieList.add(newMovie)
                    }

                    val adapter = MovieAdapter(movieList)
                    rvMovies.adapter = adapter
                    rvMovies.layoutManager = GridLayoutManager(this@MainActivity,2)

                } catch (e: Exception) {
                    Log.e("Movie Error", "JSON parsing error: ${e.message}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("Movie Error", "Failed with status $statusCode: $response")
            }
        })
    }
}
