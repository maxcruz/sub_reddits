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

package com.example.domain.models

import com.squareup.moshi.Json


data class SubReddit(

        @Json(name = "banner_img") val bannerImg: String? = null,
        @Json(name = "display_name_prefixed") val displayNamePrefixed: String? = null,
        @Json(name = "submit_text") val submitText: String? = null,
        @Json(name = "display_name") val displayName: String? = null,
        @Json(name = "header_img") val headerImg: String? = null,
        @Json(name = "title") val title: String? = null,
        @Json(name = "over18") val over18: Boolean? = null,
        @Json(name = "spoilers_enabled") val spoilersEnabled: Boolean? = null,
        @Json(name = "icon_img") val iconImg: String? = null,
        @Json(name = "header_title") val headerTitle: String? = null,
        @Json(name = "description") val description: String? = null,
        @Json(name = "subscribers") val subscribers: Int? = null,
        @Json(name = "key_color") val keyColor: String? = null,
        @Json(name = "url") val url: String? = null,
        @Json(name = "advertiser_category") val advertiserCategory: String? = null,
        @Json(name = "public_description") val publicDescription: String? = null,
        @Json(name = "subreddit_type") val subRedditType: String? = null

) {

    companion object {

        val TAG: String = SubReddit::class.java.simpleName

    }

}