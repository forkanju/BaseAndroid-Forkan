package com.learning.baseprojectforkan.data.remote.api

import com.learning.baseprojectforkan.data.remote.model.UserModel
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers(): Response<List<UserModel.UserModelItem>>
}