package com.example.domain.interactors

import co.tappsi.driver.domain.interactors.UseCase
import com.example.domain.models.SubReddit
import com.example.domain.repository.DetailContractModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.security.InvalidParameterException

class GetSubRedditById(val detailModel: DetailContractModel,
                       subscribeOn: Scheduler, observeOn: Scheduler):
        UseCase<GetSubRedditById.Input, GetSubRedditById.Output>(subscribeOn, observeOn){

    override fun executeUseCase(values: Input?): Observable<Output> {
        if (values == null) return Observable.error(InvalidParameterException())
        return detailModel.getSubRedditById(values.id).map(::Output)
    }

    data class Input(val id: String): UseCase.Input
    data class Output(val subReddit: SubReddit): UseCase.Output

}