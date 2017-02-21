package com.example.domain.interactors

import com.example.domain.interactors.UseCase
import com.example.domain.repository.ListContractModel
import io.reactivex.Observable
import io.reactivex.Scheduler

class SynchronizeRemoteData(val listModel: ListContractModel,
                            subscribeOn: Scheduler, observeOn: Scheduler):
        UseCase<UseCase.Input, UseCase.Output>(subscribeOn, observeOn) {

    override fun executeUseCase(values: Input?): Observable<Output> {
        return listModel.getRemoteEntries().map {
            listModel.clearLocalStorage()
            listModel.saveToLocalStorage(it)
            object : Output {}
        }
    }

}
