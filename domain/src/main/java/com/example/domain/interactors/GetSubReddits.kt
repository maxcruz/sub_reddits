package com.example.domain.interactors

import co.tappsi.driver.domain.interactors.UseCase
import com.example.domain.models.SubReddit
import com.example.domain.repository.ListContractModel
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetSubReddits(val listModel: ListContractModel,
                    subscribeOn: Scheduler, observeOn: Scheduler) :
        UseCase<UseCase.Input, GetSubReddits.Output>(subscribeOn, observeOn) {

    override fun executeUseCase(values: Input?): Observable<Output> {
        return listModel.getLocalEntries().map(::Output)
    }

    data class Output(val list: List<SubReddit>): UseCase.Output

}