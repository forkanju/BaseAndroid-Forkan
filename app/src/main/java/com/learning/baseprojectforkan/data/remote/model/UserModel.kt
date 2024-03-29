package com.learning.baseprojectforkan.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class UserModel : ArrayList<UserModel.Item>(){
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "avatar")
        val avatar: String,
        @Json(name = "email")
        val email: String,
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String
    )
}