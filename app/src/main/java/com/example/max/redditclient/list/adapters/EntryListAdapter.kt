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

package com.example.max.redditclient.list.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.domain.models.SubReddit
import com.example.max.redditclient.R
import com.example.max.redditclient.libraries.images.ImageLoader
import com.example.max.redditclient.list.ListContract
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


/**
 * Load each subReddit in the recycler view
 *
 * @author Max Cruz
 */
class EntryListAdapter(val view: ListContract.View,
                       val dataSet: LinkedList<SubReddit>,
                       val imageLoader: ImageLoader):
        RecyclerView.Adapter<EntryListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val subReddit = dataSet[position]
        holder?.textNamePrefixed?.text = subReddit.displayNamePrefixed
        val color = try { Color.parseColor(subReddit.keyColor) }
                    catch (ex: IllegalArgumentException) { Color.BLACK }
                    catch (ex: StringIndexOutOfBoundsException) { Color.BLACK }
        holder?.textNamePrefixed?.setTextColor(color)
        holder?.textDescription?.text = subReddit.publicDescription
        holder?.imageIcon?.let { image ->
            val url = subReddit.iconImg
            if (url != null && url.isNotEmpty()) {
                imageLoader.load(image, url)
            } else {
                image.setImageResource(R.drawable.shape_reddit_icon)
            }
            image.borderColor = color
        }
        holder?.itemView?.setOnClickListener { view.goToEntryDetail(subReddit.id) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(view.getContext())
        val itemView = inflater.inflate(R.layout.card_entry, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = dataSet.count()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.textNamePrefixed)
        lateinit var textNamePrefixed: TextView
        @BindView(R.id.textDescription)
        lateinit var textDescription: TextView
        @BindView(R.id.imageIcon)
        lateinit var imageIcon: CircleImageView

        init {
            ButterKnife.bind(this, itemView)
        }

    }

}