package com.learning.baseprojectforkan.common.data.remote.api

import com.learning.baseprojectforkan.common.data.remote.model.User
import com.learning.baseprojectforkan.common.data.remote.model.UserModel
import com.learning.baseprojectforkan.common.utils.Constants.Companion.USERS_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(USERS_ENDPOINT)
    suspend fun getUsers(): Response<List<UserModel.UserModelItem>>
}