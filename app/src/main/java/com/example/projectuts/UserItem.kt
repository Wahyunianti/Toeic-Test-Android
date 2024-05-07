package com.example.projectuts

import com.google.gson.annotations.SerializedName

data class UserItem(
    val user: List<Data>)
{
    data class Data (val id: Int?,
                     val nama: String?,
                     val username: String?,
                     val password: String?,
                     val status: String?)
}


