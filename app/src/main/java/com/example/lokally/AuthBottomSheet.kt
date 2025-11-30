package com.example.lokally

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AuthBottomSheet : BottomSheetDialogFragment() {

    private var isRegister = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isRegister = it.getBoolean(ARG_IS_REGISTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            if (isRegister) R.layout.activity_register else R.layout.activity_login,
            container,
            false
        )
    }

    companion object {
        private const val ARG_IS_REGISTER = "is_register"

        fun newInstance(isRegister: Boolean) = AuthBottomSheet().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_IS_REGISTER, isRegister)
            }
        }
    }
}