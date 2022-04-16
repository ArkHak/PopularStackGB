package com.example.popularstackgb.utils

import android.os.Handler

private data class Subscriber<T>(
    private val handler: Handler,
    private val callback: (T?) -> Unit
) {
    fun invoke(value: T?) {
        handler.post {
            callback.invoke(value)
        }
    }
}

class Publisher<T>(private val isSingle: Boolean = false) {
    private val subscribersList: MutableSet<Subscriber<T?>> = mutableSetOf()
    private var valueLast: T? = null
    private var hasFirstValue: Boolean = false

    fun subscribe(handler: Handler, callback: (T?) -> Unit) {
        val subscriber = Subscriber(handler, callback)
        subscribersList.add(subscriber)
        if (hasFirstValue) {
            subscriber.invoke(valueLast)
        }
    }

    fun unsubscribeAll() {
        subscribersList.clear()
    }

    fun post(value: T) {
        if (!isSingle) {
            hasFirstValue = true
            valueLast = value
        }
        subscribersList.forEach { it.invoke(value) }
    }
}

class SinglePublisher<T>() {
    private val subscribersList: MutableSet<Subscriber<T?>> = mutableSetOf()
    private var value: T? = null

    fun subscribe(handler: Handler, callback: (T?) -> Unit) {
        val subscriber = Subscriber(handler, callback)
        subscribersList.add(subscriber)
        subscriber.invoke(value)
    }

    fun unsubscribeAll() {
        subscribersList.clear()
    }

    fun post(value: T) {
        subscribersList.forEach { it.invoke(value) }
    }
}