package com.example.testtaskfcm.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskfcm.domain.repository.TokenRepository
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TokenRepository
) : ViewModel() {

    private var _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    init {
        fetchToken()
    }


    private fun fetchToken(){
        viewModelScope.launch {
            val tokenFcm = repository.getToken() ?: Firebase.messaging.token.await()
            Log.d("TAG", ": $tokenFcm")

            _state.value = _state.value.copy(
                token = tokenFcm ?: "Токен отсутствует",
            )
        }
    }
}