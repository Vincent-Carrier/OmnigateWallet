package com.omnigate.wallet.screens.login

import android.arch.lifecycle.ViewModel
import com.omnigate.wallet.data.Repository
import com.omnigate.wallet.models.User


class LoginViewModel(private val userRepo: Repository<User>) : ViewModel() {

}