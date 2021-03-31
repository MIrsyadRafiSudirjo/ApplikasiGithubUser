package com.example.applikasigithubuser2

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.applikasigithubuser2.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.tab_text_1,
                R.string.tab_text_2
        )

    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        val user = intent.getParcelableExtra<UserItems>(EXTRA_USER) as UserItems

        showLoading(true)
        getDetailUser(user.name.toString())

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user.name.toString()
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    fun getDetailUser(query: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$query"
        client.addHeader("Authorization", "token f528a6decebf5848d08e707c52c874f60970c138")
        client.addHeader("User-Agent", " Request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d(ContentValues.TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val username = responseObject.getString("login")
                    val avatar = responseObject.getString("avatar_url")
                    val realname = responseObject.getString("name")
                    val location = responseObject.getString("location")
                    val company = responseObject.getString("company")
                    val repository = responseObject.getString("public_repos")
                    val following = responseObject.getString("following")
                    val followers = responseObject.getString("followers")

                    bindingView(username, avatar, realname, location, company, repository, following, followers)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }


    fun bindingView(bindusername: String, bindavatar: String, bindrealname: String, bindLocation: String, bindCompany: String, bindRepository: String, bindFollowing: String, bindFollower: String) {
        val itemView = binding.avatarImageDetails

        val followText = bindFollowing
        val followrText = bindFollower
        val repoText = bindRepository
        val companyText = bindCompany
        val locaText = bindLocation
        showLoading(false)

        binding.tvUsernameDetails.text = bindusername
        Glide.with(itemView.context)
                .load(bindavatar)
                .into(binding.avatarImageDetails)
        binding.tvRealnameDetails.text = bindrealname
        binding.tvFollowers.text = followrText
        binding.tvFollowing.text = followText
        binding.tvRepository.text = repoText
        binding.tvLocation.text = locaText
        binding.tvCompany.text = companyText

        binding.tvCompanyText.text = resources.getString(R.string.company_text)
        binding.tvFollowingText.text = resources.getString(R.string.following_text)
        binding.tvFollowersText.text = resources.getString(R.string.followers_text)
        binding.tvRepositoryText.text = resources.getString(R.string.repository_text)
        binding.tvLocationText.text = resources.getString(R.string.location_text)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBarDetails.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.progressBarDetails.visibility = View.GONE
        }
    }
}