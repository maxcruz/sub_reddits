package com.example.data.dao

import com.squareup.moshi.Json

data class DataDAO(@Json(name = "children") val dataList: List<ChildDAO>)