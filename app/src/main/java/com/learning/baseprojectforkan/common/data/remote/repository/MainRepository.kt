package com.learning.baseprojectforkan.common.data.remote.repository

import com.learning.baseprojectforkan.common.data.remote.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}