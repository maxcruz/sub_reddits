/*
 * [TAPPSI SAS] LLC ("TAPPSI") CONFIDENTIAL
 * Copyright (c) 2011-2014 [TAPPSI SAS], All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains the property of TAPPSI SAS.
 * The intellectual and technical concepts contained herein are proprietary to
 * TAPPSI SAS and may be covered by U.S. and Foreign Patents, patents in process,
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material is strictly
 * forbidden unless prior written permission is obtained from TAPPSI SAS.  Access to
 * the source code contained herein is hereby forbidden to anyone except current
 * TAPPSI SAS employees, managers or contractors who have executed Confidentiality
 * and Non-disclosure agreements explicitly covering such access.
 *
 * The copyright notice above does not evidence any actual or intended publication
 * or disclosure  of  this source code, which includes information that is
 * confidential and/or proprietary, and is a trade secret, of  TAPPSI SAS.
 * ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE,
 * OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS  SOURCE CODE  WITHOUT THE EXPRESS
 * WRITTEN CONSENT OF TAPPSI SAS IS STRICTLY PROHIBITED, AND IN VIOLATION OF
 * APPLICABLE LAWS AND INTERNATIONAL TREATIES.  THE RECEIPT OR POSSESSION OF
 * THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY
 * RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE,
 * USE, OR SELL ANYTHING THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.
 */

package com.example.max.redditclient.list

import com.example.domain.exceptions.OfflineModeException
import com.example.domain.interactors.ListLocalSubReddits
import com.example.domain.interactors.SynchronizeRemoteData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

/**
 * The middle man between the view and the domain layer
 *
 * @author Max Cruz
 */
class ListPresenter(val view: ListContract.View,
                    val synchronizeRemoteData: SynchronizeRemoteData,
                    val listLocalSubReddits: ListLocalSubReddits):
        ListContract.Presenter {

    override fun synchronize() {
        view.showProgressIndicator(true)
        synchronizeRemoteData.execute().doFinally { loadSavedEntries().subscribe() }.subscribe(
                { view.showSynchronizedMessage() },
                {
                    when (it) {
                        is UnknownHostException,
                        is SocketTimeoutException,
                        is OfflineModeException -> {
                            view.showOfflineMessage()
                        }
                        else -> {
                            it.printStackTrace()
                        }
                    }
                }
        )
    }

    override fun loadSavedEntries(): Completable {
        view.clearEntries()
        return Completable.create { emitter ->
            listLocalSubReddits.execute().doFinally { view.showProgressIndicator(false) }.subscribe (
                { output -> view.appendEntries(output.list) }
            )
            emitter.onComplete()
        }.delay(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
    }

}