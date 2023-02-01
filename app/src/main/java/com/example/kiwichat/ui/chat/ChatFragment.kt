package com.example.kiwichat.ui.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.kiwichat.R
import com.example.kiwichat.common.BaseFragment
import com.example.kiwichat.databinding.FragmentChatBinding

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    val args:ChatFragmentArgs by navArgs()

    override fun viewCreated() {
        binding.info.text = args.info?.userName
    }

    override fun listeners() {

    }


}