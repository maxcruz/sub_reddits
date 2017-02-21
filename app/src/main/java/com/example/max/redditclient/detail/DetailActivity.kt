package com.example.max.redditclient.detail

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.domain.models.SubReddit
import com.example.max.redditclient.R
import com.example.max.redditclient.RedditApplication
import com.example.max.redditclient.detail.views.TextItemLayout
import com.example.max.redditclient.libraries.images.ImageLoader
import javax.inject.Inject

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

    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
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
        (application as RedditApplication).getDetailComponent(this).inject(this)
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