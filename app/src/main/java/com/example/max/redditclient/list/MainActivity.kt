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
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import com.example.data.ListRepository
import com.example.data.local.LocalStorage
import com.example.data.remote.RedditService
import com.example.data.remote.ServiceFactory
import com.example.domain.interactors.ListLocalSubReddits
import com.example.domain.interactors.SynchronizeRemoteData
import com.example.domain.models.SubReddit
import com.example.max.redditclient.R
import com.example.max.redditclient.detail.DetailActivity
import com.example.max.redditclient.libraries.images.GlideImageLoader
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

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.layoutContent)
    lateinit var mLayoutContent: CoordinatorLayout
    @BindView(R.id.swipeRefresh)
    lateinit var mSwipeRefresh: SwipeRefreshLayout
    @BindView(R.id.recyclerList)
    lateinit var mRecyclerList: RecyclerView
    @BindString(R.string.app_name)
    lateinit var appName: String

    //@Inject
    lateinit var mAdapter: EntryListAdapter
    //@Inject
    lateinit var mPresenter: ListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        toolbar.title = appName
        toolbar.setTitleTextColor(Color.WHITE)
        setupInjection()
        setupRecyclerView()
        setupSwipeRefresh()
    }

    private fun setupInjection() {
        val imageLoader = GlideImageLoader(this)
        mAdapter = EntryListAdapter(this, LinkedList(), imageLoader)
        val redditService = ServiceFactory.createService(RedditService::class.java, RedditService.BASE_URL)
        val localStorage = LocalStorage()
        val listModel = ListRepository(redditService, localStorage)
        val subscribeOn = Schedulers.io()
        val observeOn = AndroidSchedulers.mainThread()
        val synchronizeRemoteData = SynchronizeRemoteData(listModel, subscribeOn, observeOn)
        val listLocalSubReddits = ListLocalSubReddits(listModel, subscribeOn, observeOn)
        mPresenter = ListPresenter(this, synchronizeRemoteData, listLocalSubReddits)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        mRecyclerList.layoutManager = layoutManager
        mRecyclerList.adapter = mAdapter
        mPresenter.loadSavedEntries().subscribe {
            mPresenter.synchronize()
        }
    }

    private fun setupSwipeRefresh() {
        mSwipeRefresh.setOnRefreshListener({
            mPresenter.synchronize()
        })
    }

    override fun showProgressIndicator(display: Boolean) {
        if (mSwipeRefresh.isRefreshing != display) mSwipeRefresh.isRefreshing = display
    }

    override fun showSynchronizedMessage() {
        Snackbar.make(mLayoutContent, R.string.synchronized_message, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun showOfflineMessage() {
        Snackbar.make(mLayoutContent, R.string.offline_message, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .setAction(R.string.button_retry, { mPresenter.synchronize() })
                .show()
    }

    override fun clearEntries() {
        mAdapter.dataSet.clear()
        mAdapter.notifyDataSetChanged()
    }

    override fun appendEntries(list: List<SubReddit>){
        mAdapter.dataSet.addAll(list)
        mAdapter.notifyDataSetChanged()
    }

    override fun goToEntryDetail(subRedditId: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.ID_KEY, subRedditId)
        startActivity(intent)
    }

    override fun getContext(): Context = this

}