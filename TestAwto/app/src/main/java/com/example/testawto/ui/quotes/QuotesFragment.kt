package com.example.testawto.ui.quotes

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testawto.R
import com.example.testawto.data.models.Quote
import com.example.testawto.data.network.QuotesApi
import com.example.testawto.data.repositories.QuotesRepository
import kotlinx.android.synthetic.main.quotes_fragment.*
import kotlinx.android.synthetic.main.recyclerview_quote.*
import kotlinx.android.synthetic.main.recyclerview_quote.view.*


class QuotesFragment : Fragment(), RecyclerViewClickListener, View.OnClickListener {

    private lateinit var factory: QuotesViewModelFactory
    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fabUpdate.setOnClickListener(this)
        fabScroll.setOnClickListener(this)
        initData()

        rvQuotes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    fabUpdate.hide()
                    fabScroll.show()
                } else {
                    fabUpdate.show()
                    fabScroll.hide()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onRecyclerViewItemClick(view: View, quote: Quote) {
        when(view.id){
            R.id.btnTranslate -> {
                viewModel.translate(quote.id)
                viewModel.quotes.observe(viewLifecycleOwner, Observer { quotes ->
                    rvQuotes.adapter!!.notifyDataSetChanged()
                })
            }
            R.id.btnVote -> {
                buttonAction("Gracias por votar por " + quote.author, R.color.colorRed, view.btnVote)
            }
            R.id.btnShare -> {
                buttonAction("Gracias por compartir la cita de " + quote.author, R.color.colorAccent, view.btnShare)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fabUpdate -> {
                initData()
            }
            R.id.fabScroll -> {
                rvQuotes.post {
                    rvQuotes.smoothScrollToPosition(0)
                }
            }
        }
    }

    private fun initData() {
        progressBarHolder.visibility = View.VISIBLE
        fabScroll.hide()
        val api = QuotesApi()
        val repository = QuotesRepository(api)
        factory = QuotesViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)
        viewModel.getQuotes()
        viewModel.quotes.observe(viewLifecycleOwner, Observer { quotes ->
            rvQuotes.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = QuotesAdapter(quotes, this)
                progressBarHolder.visibility = View.GONE
            }
        })
    }

    fun buttonAction(message: String, color: Int, button: Button){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        button.isEnabled = false
        val drawablesprev: Array<Drawable> = button.getCompoundDrawables()
        drawablesprev[0].setColorFilter(resources.getColor(color), PorterDuff.Mode.SRC_ATOP)
    }
}
