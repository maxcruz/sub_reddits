package com.example.data.dao

import com.squareup.moshi.Json

data class RedditsDAO(@Json(name = "data") val data: DataDAO)