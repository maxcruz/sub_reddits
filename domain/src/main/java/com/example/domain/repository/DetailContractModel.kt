package com.example.domain.repository

import com.example.domain.models.SubReddit
import io.reactivex.Observable

interface DetailContractModel {

    fun getSubRedditById(id: String): Observable<SubReddit>

}