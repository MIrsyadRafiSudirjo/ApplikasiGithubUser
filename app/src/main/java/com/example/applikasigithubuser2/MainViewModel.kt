package com.example.applikasigithubuser2

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val listUser = MutableLiveData<ArrayList<UserItems>>()

    fun setUsers(query: String) {
        val listUserItems = ArrayList<UserItems>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$query"
        client.addHeader("Authorization", "token f528a6decebf5848d08e707c52c874f60970c138")
        client.addHeader("User-Agent", " Request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val responseObjects = JSONObject(result)
                    val items = responseObjects.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = UserItems()
                        user.avatar = avatar
                        user.name = username
                        listUserItems.add(user)
                    }
                    listUser.postValue(listUserItems)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<UserItems>> {
        return listUser
    }

    private val listFollower = MutableLiveData<ArrayList<FollowerItems>>()


    fun setFollowers(query: String) {
        val listFollowerItems = ArrayList<FollowerItems>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$query/followers"
        client.addHeader("Authorization", "token f528a6decebf5848d08e707c52c874f60970c138")
        client.addHeader("User-Agent", " Request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = FollowerItems()
                        user.avatarfwr = avatar
                        user.namefwr = username
                        listFollowerItems.add(user)
                    }
                    listFollower.postValue(listFollowerItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }


    fun getFollowers(): LiveData<ArrayList<FollowerItems>> {
        return listFollower
    }


    private val listFollowing = MutableLiveData<ArrayList<FollowingItems>>()

    fun setFollowing(query: String) {
        val listFollowingItems = ArrayList<FollowingItems>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$query/following"
        client.addHeader("Authorization", "token f528a6decebf5848d08e707c52c874f60970c138")
        client.addHeader("User-Agent", " Request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = FollowingItems()
                        user.avatarfwl = avatar
                        user.namefwl = username
                        listFollowingItems.add(user)
                    }
                    listFollowing.postValue(listFollowingItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<FollowingItems>> {
        return listFollowing
    }

}