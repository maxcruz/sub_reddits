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

package com.example.data.dao

import com.example.data.local.AppDataBase
import com.example.domain.models.SubReddit
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import com.squareup.moshi.Json

@Table(database = AppDataBase::class)
class SubRedditDAO(): BaseModel() {

    @PrimaryKey
    @Json(name = "id")
    lateinit var id: String

    @Column
    @Json(name = "display_name_prefixed")
    lateinit var displayNamePrefixed: String

    @Column
    @Json(name = "public_description")
    lateinit var publicDescription: String

    @Column
    @Json(name = "key_color")
    var keyColor: String? = null

    @Column
    @Json(name = "icon_img")
    var iconImg: String? = null

    @Column
    @Json(name = "banner_img")
    var bannerImg: String? = null

    @Column
    @Json(name = "submit_text")
    var submitText: String? = null

    @Column
    @Json(name = "display_name")
    var displayName: String? = null

    @Column
    @Json(name = "header_img")
    var headerImg: String? = null

    @Column
    @Json(name = "title")
    var title: String? = null

    @Column
    @Json(name = "header_title")
    var headerTitle: String? = null

    @Column
    @Json(name = "description")
    var description: String? = null

    @Column
    @Json(name = "subscribers")
    var subscribers: Int? = null

    @Column
    @Json(name = "url")
    var url: String? = null

    @Column
    @Json(name = "advertiser_category")
    var advertiserCategory: String? = null

    @Column
    @Json(name = "subreddit_type")
    var subRedditType: String? = null

    constructor(subReddit: SubReddit): this() {
        id = subReddit.id
        displayNamePrefixed = subReddit.displayNamePrefixed
        publicDescription = subReddit.publicDescription
        keyColor = subReddit.keyColor
        iconImg = subReddit.iconImg
        bannerImg = subReddit.bannerImg
        submitText = subReddit.submitText
        displayName = subReddit.displayName
        headerImg = subReddit.headerImg
        title = subReddit.title
        headerTitle = subReddit.headerTitle
        description = subReddit.description
        subscribers = subReddit.subscribers
        url = subReddit.url
        advertiserCategory = subReddit.advertiserCategory
        subRedditType = subReddit.subRedditType
    }

    fun toSubReddit() = SubReddit(
            id = id,
            displayNamePrefixed = displayNamePrefixed,
            publicDescription = publicDescription,
            keyColor = keyColor,
            iconImg = iconImg,
            bannerImg = bannerImg,
            submitText = submitText,
            displayName = displayName,
            headerImg = headerImg,
            title = title,
            headerTitle = headerTitle,
            description = description,
            subscribers = subscribers,
            url = url,
            advertiserCategory = advertiserCategory,
            subRedditType = subRedditType
    )

}