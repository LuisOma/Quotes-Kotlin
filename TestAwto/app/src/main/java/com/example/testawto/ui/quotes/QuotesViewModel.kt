package com.example.testawto.ui.quotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testawto.data.models.Quote
import com.example.testawto.data.repositories.QuotesRepository
import com.example.testawto.util.Coroutines
import kotlinx.coroutines.Job

class QuotesViewModel(
    private val repository: QuotesRepository
) : ViewModel() {

    private lateinit var job: Job

    private val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>>
        get() = _quotes

    fun getQuotes() {
        job = Coroutines.ioThenMain(
            { repository.getQuotes() },
            { _quotes.value = it }
        )
    }

    fun translate(id: String) {
        quotes.value!!.forEach {
            if(it.id == id) {
                val aux = it.sr
                it.sr = it.en
                it.en = aux
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}