package com.learning.baseprojectforkan.data.remote.repository

import com.learning.baseprojectforkan.data.remote.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}