package com.example.testtaskfcm.domain.repository

interface TokenRepository {

    fun saveToken(token: String)

    fun getToken(): String?
}