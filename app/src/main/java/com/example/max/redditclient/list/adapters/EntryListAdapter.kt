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