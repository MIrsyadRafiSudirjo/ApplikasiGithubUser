package com.example.applikasigithubuser2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applikasigithubuser2.databinding.FragmentFollowerBinding


class FollowerFragment : Fragment() {

    private lateinit var fbinding: FragmentFollowerBinding
    private lateinit var adapter: FollowerAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFollowerBinding.bind(view)
        fbinding = binding

        adapter = FollowerAdapter()
        adapter.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        fbinding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        fbinding.rvFollowers.adapter = adapter

        val query = arguments?.getString(USER_NAME)
        if (query != null) {
            mainViewModel.setFollowers(query)
        }

        mainViewModel.getFollowers().observe(viewLifecycleOwner, { user ->
            if (user != null) {
                adapter.setData(user)
            }
        })
    }


    companion object {
        private val USER_NAME = "user_name"
        fun newInstance(username: String): FollowerFragment {
            val fragment = FollowerFragment()
            val mbundle = Bundle()
            mbundle.putString(USER_NAME, username)
            fragment.arguments = mbundle
            return fragment
        }
    }
}