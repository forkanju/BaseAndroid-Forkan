package com.learning.baseprojectforkan.data.remote.api

import com.learning.baseprojectforkan.data.remote.model.UserModel
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<UserModel.Item>> = apiService.getUsers()

}