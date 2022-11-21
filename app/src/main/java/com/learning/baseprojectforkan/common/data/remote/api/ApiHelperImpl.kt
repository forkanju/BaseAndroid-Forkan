package com.learning.baseprojectforkan.common.data.remote.api

import com.learning.baseprojectforkan.common.data.remote.model.UserModel
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<UserModel.UserModelItem>> = apiService.getUsers()

}