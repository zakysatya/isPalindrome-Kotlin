package com.zs.ispalindrome.ui

import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.zs.ispalindrome.R
import com.zs.ispalindrome.adapter.UsernameAdapter
import com.zs.ispalindrome.databinding.ActivityThirdScreenBinding
import com.zs.ispalindrome.model.User


class ThirdScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdScreenBinding

    private lateinit var users : ArrayList<User>
    private lateinit var adapter: UsernameAdapter

    private var currentPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        users = ArrayList()
        adapter = UsernameAdapter(users, sharedPreferences)

        initState()
        updateRecyclerView()
        fetchData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            fetchData()
        }

        binding.btnBackToSecondScreen.setOnClickListener{
            onBackPressed()
        }

        // if (isLoading) binding.progressBar.visibility = View.VISIBLE else View.GONE
    }

    private fun updateRecyclerView(){
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && layoutManager.findLastVisibleItemPosition() == users.size - 1) {
                    currentPage++
                    fetchData()
                }
            }
        })
    }

    private fun fetchData() {
        if (isLoading) return

        isLoading = true
        binding.swipeRefreshLayout.isRefreshing = true

        val url = "https://reqres.in/api/users?page=$currentPage&per_page=8"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val dataArray = response.getJSONArray("data")
                val totalPages = response.getInt("total_pages")
               // val totalItems = response.getInt("total")

                if (currentPage == 1) users.clear()
                if (currentPage == totalPages)  Toast.makeText(this, "Total Page Loaded: $totalPages Pages", Toast.LENGTH_SHORT).show()

                for (i in 0 until dataArray.length()) {
                    val userObject = dataArray.getJSONObject(i)
                    val user = User(
                        email = userObject.getString("email"),
                        firstName = userObject.getString("first_name"),
                        lastName = userObject.getString("last_name"),
                        avatar = userObject.getString("avatar")
                    )
                    //Toast.makeText(this, user.firstName, Toast.LENGTH_SHORT).show()
                    users.add(user)
                }
                if(dataArray.length() > 0) Toast.makeText(this, "Item Loaded: ${dataArray.length()}, Item per-Page: 8", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                binding.tvEmptyState.visibility = if (users.isEmpty()) View.VISIBLE else View.GONE
                binding.swipeRefreshLayout.isRefreshing = false
                isLoading = false
            },
            { error ->
                error.printStackTrace()
                binding.swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this, "Can't get from API", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        )
        Volley.newRequestQueue(this).add(request)
    }

    private fun initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = getColor(R.color.white)
            window.navigationBarColor = getColor(R.color.white)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

}