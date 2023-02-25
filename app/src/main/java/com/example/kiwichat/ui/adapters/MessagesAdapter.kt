package com.example.kiwichat.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.kiwichat.R
import com.example.kiwichat.data.User
import com.example.kiwichat.databinding.ReceivedBinding
import com.example.kiwichat.databinding.SentBinding
import com.example.kiwichat.databinding.SingleUserLayoutBinding



class MessagesAdapter (val context: Context,val messageList:List<User.Messages>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return when(viewType){
            R.layout.sent -> HomeRecyclerViewHolder.SentViewHolder(
                SentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.received -> HomeRecyclerViewHolder.ReceivedViewHolder(
                ReceivedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemCount() = messageList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return when(messageList[position]){
            is HomeRecyclerViewItem.Director -> R.layout.item_director
            is HomeRecyclerViewItem.Movie -> R.layout.item_movie
        }
    }

    sealed class HomeRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        class SentViewHolder(private val binding: SentBinding) : HomeRecyclerViewHolder(binding){
            fun bind(message: User.Messages){
                binding.tvSentMessage.text = message.text
            }
        }

        class ReceivedViewHolder(private val binding: ReceivedBinding) : HomeRecyclerViewHolder(binding){
            fun bind(message: User.Messages){
                binding.tvReceivedMessage.text = message.text
            }
        }

    }

}
