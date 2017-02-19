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

package com.example.max.redditclient.detail

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.data.DetailRepository
import com.example.data.local.LocalStorage
import com.example.domain.interactors.GetSubRedditById
import com.example.domain.models.SubReddit
import com.example.max.redditclient.R
import com.example.max.redditclient.detail.views.TextItemLayout
import com.example.max.redditclient.libraries.images.GlideImageLoader
import com.example.max.redditclient.libraries.images.ImageLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailActivity: AppCompatActivity(), DetailContract.View {

    companion object {
        val ID_KEY = "subRedditIdKey"
    }

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.imageBanner)
    lateinit var imageBanner: ImageView
    @BindView(R.id.itemName)
    lateinit var itemName: TextItemLayout
    @BindView(R.id.itemTitle)
    lateinit var itemTitle: TextItemLayout
    @BindView(R.id.itemSubscribers)
    lateinit var itemSubscribers: TextItemLayout
    @BindView(R.id.itemCategory)
    lateinit var itemCategory: TextItemLayout
    @BindView(R.id.itemDescription)
    lateinit var itemDescription: TextItemLayout

    //@Inject
    lateinit var mImageLoader: ImageLoader
    //@Inject
    lateinit var mPresenter: DetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        setupInjection()
        val idSubReddit = intent.getStringExtra(ID_KEY)
        mPresenter.getSubReddit(idSubReddit)
    }

    private fun setupInjection() {
        val localStorage = LocalStorage()
        val detailModel = DetailRepository(localStorage)
        val subscribeOn = Schedulers.io()
        val observeOn = AndroidSchedulers.mainThread()
        val getSubRedditById = GetSubRedditById(detailModel, subscribeOn, observeOn)
        mPresenter = DetailPresenter(this, getSubRedditById)
        mImageLoader = GlideImageLoader(this)
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPause() {
        super.onPause()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.onBackPressed()
        }
        return true
    }

    override fun loadContentData(subReddit: SubReddit) {
        val bannerImg = subReddit.bannerImg
        val displayNamePrefixed = subReddit.displayNamePrefixed
        val title = subReddit.title
        val subscribers = subReddit.subscribers
        val advertiserCategory = subReddit.advertiserCategory
        val submitText = subReddit.submitText
        toolbar.title = subReddit.headerTitle
        bannerImg?.let { mImageLoader.load(imageBanner, bannerImg) }
        itemName.setValue(displayNamePrefixed)
        title?.let { itemTitle.setValue(title) }
        subscribers?.let { itemSubscribers.setValue(subscribers.toString()) }
        advertiserCategory?.let { itemCategory.setValue(advertiserCategory) }
        submitText?.let { itemDescription.setValue(submitText) }
    }

    override fun getContext(): Context = this

}