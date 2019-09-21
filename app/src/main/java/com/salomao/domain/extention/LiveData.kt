package com.salomao.domain.extention

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.salomao.data.pojo.Event

fun <T> LiveData<Event<T>>.observeEventNotHandled(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer<Event<T>> { event ->
        event?.getContentIfNotHandled()?.let { content ->
            observer(content)
        }
    })
}