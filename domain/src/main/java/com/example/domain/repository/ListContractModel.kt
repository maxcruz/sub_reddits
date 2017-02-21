package com.example.domain.repository

import com.example.domain.models.SubReddit
import io.reactivex.Observable

interface ListContractModel {

   fun getRemoteEntries(): Observable<List<SubReddit>>
   fun saveToLocalStorage(list: List<SubReddit>)
   fun clearLocalStorage()
   fun getLocalEntries(): Observable<List<SubReddit>>

}