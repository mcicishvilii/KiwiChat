package com.example.kiwichat.ui.main


import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiwichat.R
import com.example.kiwichat.common.BaseFragment
import com.example.kiwichat.data.User
import com.example.kiwichat.databinding.FragmentMainBinding
import com.example.kiwichat.ui.adapters.UsersAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

const val TAG = "mcicishvilii@"

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private val linksAdapter: UsersAdapter by lazy { UsersAdapter() }

    val listOfUsers = mutableListOf<User>()



    override fun viewCreated() {
        auth = Firebase.auth
        db =  Firebase.database.reference

        getData()
        setupRecycler()
        populateList()
    }

    override fun listeners() {
        binding.tvUserName.setOnClickListener {
            logout()
        }
    }

    private fun logout(){
        auth.signOut()
        findNavController().navigate(R.id.action_mainFragment_to_registerFragment)
    }

    private fun setupRecycler() {
        binding.rvUsers.apply {
            adapter = linksAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }


    private fun getData(){

        lifecycleScope.launch{
            db.child("Users").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val users: User? = ds.getValue(User::class.java)
                        binding.tvUserName.text = users?.email.toString()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("FirebaseDatabase", "Error reading data from database", error.toException())
                }
            })
        }
    }

    private fun populateList(){
        lifecycleScope.launch{
            db.child("Users").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val users: User? = ds.getValue(User::class.java)
//                        binding.tvUserName.text = users?.email.toString()
                        listOfUsers.add(users!!)
                        linksAdapter.submitList(listOfUsers)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("FirebaseDatabase", "Error reading data from database", error.toException())
                }
            })
        }
    }
}