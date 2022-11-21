package com.learning.baseprojectforkan.common.data.remote.api

import com.learning.baseprojectforkan.common.data.remote.model.UserModel
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers(): Response<List<UserModel.UserModelItem>>
}