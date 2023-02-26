package com.example.kiwichat.ui.chat

import MessagesAdapter

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiwichat.R
import com.example.kiwichat.common.BaseFragment
import com.example.kiwichat.data.Messages
import com.example.kiwichat.data.User
import com.example.kiwichat.databinding.FragmentChatBinding
import com.example.kiwichat.ui.adapters.UsersAdapter
import com.example.kiwichat.ui.main.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {



    private var messagesList = mutableListOf<Messages>()
    private val messagesAdapter: MessagesAdapter by lazy {
        MessagesAdapter(requireContext(), messagesList)
    }

    val args: ChatFragmentArgs by navArgs()
    private lateinit var db: DatabaseReference

    var senderRoom: String? = null
    var receiverRoom: String? = null

    val senderUid = FirebaseAuth.getInstance().currentUser?.uid

    override fun viewCreated() {
        val receiverUid = args.userInfo!!.id
        messagesList = mutableListOf()
        db = Firebase.database.reference
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        setupRecycler()
        displayMessage()

    }

    override fun listeners() {
        sendMessage()
    }

    private fun setupRecycler() {

        binding.rvMessages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMessages.adapter = messagesAdapter
    }

    private fun displayMessage() {
        db.child("Chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagesList.clear()
                    for (ps in snapshot.children) {
                        val message = ps.getValue(Messages::class.java)
                        messagesList.add(message!!)
                    }
                    messagesAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun sendMessage() {
        binding.btnSendMessage.setOnClickListener {
            val messageText = binding.etMessageBox.text.toString()
            val message = Messages(senderUid!!, messageText)

            db.child("Chats").child(senderRoom!!).child("messages").push()
                .setValue(message).addOnSuccessListener {
                    db.child("Chats").child(receiverRoom!!).child("messages").push()
                        .setValue(message)
                }
            binding.etMessageBox.setText("")
        }
    }


}