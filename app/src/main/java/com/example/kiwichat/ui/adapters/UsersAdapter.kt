package com.example.kiwichat.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kiwichat.data.User
import com.example.kiwichat.databinding.SingleUserLayoutBinding


class UsersAdapter :
    ListAdapter<User, UsersAdapter.UsersViewHolder>(
        LinksDiffCallback()
    ) {

    private lateinit var itemClickListener: (User, Int) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UsersViewHolder {
        val binding =
            SingleUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindData()
    }


    inner class UsersViewHolder(private val binding: SingleUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var model: User? = null
        fun bindData() {
            model = getItem(bindingAdapterPosition)
            binding.apply {

                val string = model?.userName
                tvUser.text = string
            }

            binding.tvUser.setOnClickListener {
                itemClickListener.invoke(model!!, absoluteAdapterPosition)
            }

        }

    }

    fun setOnItemClickListener(clickListener: (User, Int) -> Unit) {
        itemClickListener = clickListener
    }
}


class LinksDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(
        oldItem: User,
        newItem: User,
    ): Boolean {
        return oldItem.userName == newItem.userName
    }

    override fun areContentsTheSame(
        oldItem: User,
        newItem: User,
    ): Boolean {
        return oldItem == newItem
    }
}