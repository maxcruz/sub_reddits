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
import com.example.domain.models.SubReddit
import com.example.max.redditclient.R
import com.example.max.redditclient.RedditApplication
import com.example.max.redditclient.detail.DetailActivity
import com.example.max.redditclient.list.adapters.EntryListAdapter
import javax.inject.Inject

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

    @Inject
    lateinit var mAdapter: EntryListAdapter
    @Inject
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
        (application as RedditApplication).getListComponent(this).inject(this)
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