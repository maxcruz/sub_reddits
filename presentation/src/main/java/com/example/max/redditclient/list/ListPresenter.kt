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
import com.example.domain.interactors.GetSubReddits
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * The middle man between the view and the domain layer
 *
 * @author Max Cruz
 */
class ListPresenter(val view: ListContract.View, val getEntriesUseCase: GetSubReddits):
        ListContract.Presenter {

    override fun loadEntries() {
        view.showProgressIndicator(true)
        getEntriesUseCase.execute().subscribe(
                { output ->
                    view.showProgressIndicator(false)
                    Observable.fromIterable(output.reddits).debounce(1, TimeUnit.SECONDS)
                            .subscribe { entry -> view.appendEntry(entry) }
                },
                { error ->
                    view.showProgressIndicator(false)
                    if (error is OfflineModeException) {
                        view.showOfflineMessage()
                    }
                }
        )

    }

}