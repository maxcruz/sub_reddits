package com.example.data

import com.example.data.dao.SubRedditDAO
import com.example.data.dao.SubRedditDAO_Table
import com.example.data.local.LocalStorage
import com.example.domain.models.SubReddit
import com.example.domain.repository.DetailContractModel
import io.reactivex.Observable

class DetailRepository(val localStorage: LocalStorage): DetailContractModel {

    override fun getSubRedditById(id: String): Observable<SubReddit> {
        val model = localStorage.retrieveSingleItem(SubRedditDAO::class.java,
                SubRedditDAO_Table.id.eq(id))
        return Observable.just((model as SubRedditDAO).toSubReddit())
    }

}