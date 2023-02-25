package com.example.kiwichat.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.kiwichat.R
import com.example.kiwichat.common.BaseFragment
import com.example.kiwichat.data.User
import com.example.kiwichat.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun viewCreated() {
        auth = Firebase.auth
        ifLoggedIn()
        checkVisibility()
    }


    override fun listeners() {
        registerUser()
        navigateToSignIn()

    }


    private fun navigateToSignIn(){
        binding.tvSignin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToSignInFragment())
        }
    }
    private fun registerUser(){
        binding.btnNext.setOnClickListener {
            val email = binding.etEmailImpl.text.toString()
            val password = binding.etPasswordImpl.text.toString()
            val username = binding.etUserNameImpl.text.toString()
            RegisterUser(username,email,password)
        }
    }
    private fun checkVisibility(){
        binding.etEmailImpl.doOnTextChanged { text, start, before, count ->
            binding.btnNext.visibility = View.VISIBLE
        }
    }

    private fun ifLoggedIn(){
        val user = Firebase.auth.currentUser
        if (user != null) {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToMainFragment())
        } else {
//            findNavController().navigate(R.id.action_registerFragment_to_signInFragment)
        }
    }

    private fun RegisterUser(username:String,email:String,password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign up success, update UI with the signed-in user's information
//                    val user = FirebaseAuth.getInstance().currentUser

                    writeNewUser(username,email,auth.currentUser?.uid!!)
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                } else {
                    Log.w("FirebaseAuth", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun writeNewUser(name:String,email:String,uid:String) {
        db =  Firebase.database.reference
        val dbUser = User(uid,name,email)
        db.child("Users").child(uid).setValue(dbUser )
    }


}