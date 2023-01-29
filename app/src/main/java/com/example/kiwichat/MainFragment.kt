package com.example.kiwichat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.contus.flycommons.Constants.USER_IDENTIFIER
import com.contusflysdk.api.FlyCore
import com.example.kiwichat.common.BaseFragment
import com.example.kiwichat.databinding.FragmentMainBinding
import org.json.JSONObject

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    override fun viewCreated() {
        FlyCore.registerUser(USER_IDENTIFIER) { isSuccess, throwable, data ->
            if(isSuccess) {
                val isNewUser = data["is_new_user"] as Boolean
                val responseObject = data.get("data") as JSONObject
                Log.d("misho", isNewUser.toString())
                Log.d("misho", responseObject.toString())
            } else {
                Log.d("misho", "failed")
            }
        }
    }

    override fun listeners() {

    }


}