package com.example.max.redditclient.detail.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.max.redditclient.R

class TextItemLayout(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    @BindView(R.id.imageIcon)
    lateinit var imageIcon: ImageView
    @BindView(R.id.textLabel)
    lateinit var textLabel: TextView
    @BindView(R.id.textValue)
    lateinit var textValue: TextView

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val view = View.inflate(context, R.layout.layout_text_item, this)
        ButterKnife.bind(this, view)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextItemLayout)
        val color = typedArray.getColor(R.styleable.TextItemLayout_itemTint, android.R.color.black)
        imageIcon.setImageDrawable(typedArray.getDrawable(R.styleable.TextItemLayout_itemIcon))
        imageIcon.setColorFilter(color)
        val labelResource = typedArray.getResourceId(R.styleable.TextItemLayout_itemLabel,
                R.string.common_default)
        textLabel.text = context.getString(labelResource)
        val valueResource = typedArray.getResourceId(R.styleable.TextItemLayout_itemValue,
                R.string.common_default)
        textValue.text = context.getString(valueResource)
        textLabel.setTextColor(color)
        typedArray.recycle()
    }

    fun setValue(value: String) {
        textValue.text = value
    }

}

