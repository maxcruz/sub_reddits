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

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.data.ListRepository
import com.example.data.remote.RedditService
import com.example.data.remote.ServiceFactory
import com.example.domain.interactors.GetSubReddits
import com.example.domain.models.SubReddit
import com.example.max.redditclient.R
import com.example.max.redditclient.detail.DetailActivity
import com.example.max.redditclient.extensions.putExtraModel
import com.example.max.redditclient.list.adapters.EntryListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * View for the list of reddits from Reddit
 *
 * @author Max Cruz
 */
class MainActivity: AppCompatActivity(), ListContract.View {

    @BindView(R.id.layoutContent)
    lateinit var mLayoutContent: CoordinatorLayout
    @BindView(R.id.swipeRefresh)
    lateinit var mSwipeRefresh: SwipeRefreshLayout
    @BindView(R.id.recyclerList)
    lateinit var mRecyclerList: RecyclerView

    //@Inject
    lateinit var mAdapter: EntryListAdapter
    //@Inject
    lateinit var mPresenter: ListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setupInjection()
        setupRecyclerView()
        setupSwipeRefresh()
    }

    /**
     *
     */
    private fun setupInjection() {
        mAdapter = EntryListAdapter(this, LinkedList())
        val redditService = ServiceFactory.createService(RedditService::class.java, RedditService.BASE_URL)
        val listModel = ListRepository(redditService)
        val subscribeOn = Schedulers.io()
        val observeOn = AndroidSchedulers.mainThread()
        val useCase = GetSubReddits(listModel, subscribeOn, observeOn)
        mPresenter = ListPresenter(this, useCase)
    }

    /**
     *
     */
    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this, 2)
        mRecyclerList.layoutManager = layoutManager
        mRecyclerList.adapter = mAdapter
        mPresenter.loadEntries()
    }

    /**
     *
     */
    private fun setupSwipeRefresh() {
        mSwipeRefresh.setOnRefreshListener({
            mPresenter.loadEntries()
        })
    }

    override fun showProgressIndicator(display: Boolean) {
        if (mSwipeRefresh.isRefreshing != display) mSwipeRefresh.isRefreshing = display
    }

    override fun showOfflineMessage() {
        Snackbar.make(mLayoutContent, R.string.offline_message, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .setAction(R.string.button_retry, { mPresenter.loadEntries() })
                .show()
    }

    override fun appendEntry(subReddit: SubReddit) = mAdapter.addEntry(subReddit)

    override fun goToEntryDetail(subReddit: SubReddit) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtraModel(SubReddit.TAG, subReddit)
        startActivity(intent)
    }

    override fun getContext(): Context = this

}