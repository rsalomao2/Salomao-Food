package com.salomao.domain.extention

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.salomao.data.pojo.Event

fun <T> LiveData<Event<T>>.observeEventNotHandled(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer<Event<T>> { event ->
        event?.getContentIfNotHandled()?.let { content ->
            observer(content)
        }
    })
}

fun <T> LiveData<T>.observeIfNotNull(lifecycleOwner: LifecycleOwner, onChanged: (t: T) -> Unit) {
    this.observe(lifecycleOwner, Observer { event ->
        event?.let { onChanged(it) }
    })
}

fun <T : Any> mutableLiveData(initialValue: T? = null): MutableLiveData<T> {
    return MutableLiveData<T>().apply { value = initialValue }
}