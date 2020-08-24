package com.example.testawto.ui.quotes

import android.view.View
import com.example.testawto.data.models.Quote

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, quote: Quote)
}