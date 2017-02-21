package com.example.domain.models

data class SubReddit(
        val id: String,
        val displayNamePrefixed: String,
        val publicDescription: String,
        val keyColor: String? = null,
        val bannerImg: String? = null,
        val headerTitle: String? = null,
        val title: String? = null,
        val advertiserCategory: String? = null,
        val submitText: String? = null,
        val subscribers: Int? = null,
        val iconImg: String? = null
)