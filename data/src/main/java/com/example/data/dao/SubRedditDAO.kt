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
        title = subReddit.title
        headerTitle = subReddit.headerTitle
        subscribers = subReddit.subscribers
        advertiserCategory = subReddit.advertiserCategory
    }

    fun toSubReddit() = SubReddit(
            id = id,
            displayNamePrefixed = displayNamePrefixed,
            publicDescription = publicDescription,
            keyColor = keyColor,
            iconImg = iconImg,
            bannerImg = bannerImg,
            submitText = submitText,
            title = title,
            headerTitle = headerTitle,
            subscribers = subscribers,
            advertiserCategory = advertiserCategory
    )

}