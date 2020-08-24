package com.example.testawto.ui.quotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testawto.R
import com.example.testawto.data.models.Quote
import com.example.testawto.databinding.RecyclerviewQuoteBinding

class QuotesAdapter (
    private val quotes: List<Quote>,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>(){

    override fun getItemCount() = quotes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuotesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.
                recyclerview_quote,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.recyclerviewQuoteBinding.quote = quotes[position]
        holder.recyclerviewQuoteBinding.btnTranslate.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.recyclerviewQuoteBinding.btnTranslate, quotes[position])
        }
        holder.recyclerviewQuoteBinding.btnVote.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.recyclerviewQuoteBinding.btnVote, quotes[position])
        }
        holder.recyclerviewQuoteBinding.btnShare.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.recyclerviewQuoteBinding.btnShare, quotes[position])
        }
    }

    inner class QuotesViewHolder(
        val recyclerviewQuoteBinding: RecyclerviewQuoteBinding
    ) : RecyclerView.ViewHolder(recyclerviewQuoteBinding.root)

}