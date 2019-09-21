package com.salomao.domain.extention

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

/*
* Class developed by Pedro a friend of my where I´ve learned a lot
* all rights for him
* */

fun <T> Task<T>.asDeferredAsync(): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    deferred.invokeOnCompletion {
        if (deferred.isCancelled) {
            deferred.completeExceptionally(CancellationException())
        }
    }

    this.addOnSuccessListener { result -> deferred.complete(result) }
    this.addOnFailureListener { exception -> deferred.completeExceptionally(exception) }

    return deferred
}
