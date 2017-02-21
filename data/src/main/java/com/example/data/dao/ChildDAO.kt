package com.example.data.dao

import com.squareup.moshi.Json

data class ChildDAO(

    @Json(name = "data") val subReddit: SubRedditDAO

)