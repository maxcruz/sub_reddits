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

package com.example.max.redditclient

import android.app.Application
import com.example.data.local.injector.LocalStorageModule
import com.example.data.remote.injector.RedditServiceModule
import com.example.max.redditclient.detail.DetailContract
import com.example.max.redditclient.detail.injector.DaggerDetailComponent
import com.example.max.redditclient.detail.injector.DetailComponent
import com.example.max.redditclient.detail.injector.DetailModule
import com.example.max.redditclient.libraries.images.injector.ImageLoaderModule
import com.example.max.redditclient.list.ListContract
import com.example.max.redditclient.list.injector.DaggerListComponent
import com.example.max.redditclient.list.injector.ListComponent
import com.example.max.redditclient.list.injector.ListModule
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager


class RedditApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(FlowConfig.Builder(this).openDatabasesOnInit(true).build())
    }

    fun getListComponent(view: ListContract.View): ListComponent {
        return DaggerListComponent.builder()
                .imageLoaderModule(ImageLoaderModule(view.getContext()))
                .redditServiceModule(RedditServiceModule())
                .localStorageModule(LocalStorageModule())
                .listModule(ListModule(view))
                .build()
    }

    fun getDetailComponent(view: DetailContract.View): DetailComponent {
        return DaggerDetailComponent.builder()
                .imageLoaderModule(ImageLoaderModule(view.getContext()))
                .localStorageModule(LocalStorageModule())
                .detailModule(DetailModule(view))
                .build()
    }

}