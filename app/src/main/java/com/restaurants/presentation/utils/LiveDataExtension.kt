@file:Suppress("unused")

package com.restaurants.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Set value only if previous was null or not set
 */
fun <T> MutableLiveData<T>.setValueIfEmpty(value: T?) {
    if (this.value == null) {
        setValueIgnoreNull(value)
    }
}

/**
 * Set value only if new is not null
 */
fun <T> MutableLiveData<T>.setValueIgnoreNull(value: T?) {
    value?.let { setValue(value) }
}

/**
 * Set value if value is not null
 * */
fun <T> LiveData<T>.defaultObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) =
    this.observe(owner, Observer { it.let(observer) })

fun <T1> observeForever(
    p1: LiveData<T1>,
    block: (T1) -> Unit
)
        : OneTimeActionWithParameter<T1> {

    val action = OneTimeActionWithParameter<T1>() {
        block(it)
    }

    p1.observeForever {
        action.invoke(it)
    }

    return action
}

fun <T1, T2> observeForever(
    p1: LiveData<T1>,
    p2: LiveData<T2>,
    block: (T1, T2) -> Unit
)
        : OneTimeActionWithParameter<Pair<T1, T2>> {

    val action = OneTimeActionWithParameter<Pair<T1, T2>>() {
        block(it.first, it.second)
    }

    p1.observeForever {
        if (p1.value != null && p2.value != null) {
            action.invoke(Pair(p1.value!!, p2.value!!))
        }
    }
    p2.observeForever {
        if (p1.value != null && p2.value != null) {
            action.invoke(Pair(p1.value!!, p2.value!!))
        }
    }

    return action
}

fun <T1, T2, T3> observeForever(
    p1: LiveData<T1>,
    p2: LiveData<T2>,
    p3: LiveData<T3>,
    block: (T1, T2, T3) -> Unit
)
        : OneTimeActionWithParameter<Triple<T1, T2, T3>> {
    val action = OneTimeActionWithParameter<Triple<T1, T2, T3>>() {
        block(it.first, it.second, it.third)
    }

    p1.observeForever {
        if (p1.value != null && p2.value != null && p3.value != null) {
            action.invoke(Triple(p1.value!!, p2.value!!, p3.value!!))
        }
    }
    p2.observeForever {
        if (p1.value != null && p2.value != null && p3.value != null) {
            action.invoke(Triple(p1.value!!, p2.value!!, p3.value!!))
        }
    }
    p3.observeForever {
        if (p1.value != null && p2.value != null && p3.value != null) {
            action.invoke(Triple(p1.value!!, p2.value!!, p3.value!!))
        }
    }

    return action
}

fun observeArgForever(
    vararg array: LiveData<out Any>,
    block: (Array<Any>) -> Unit
) {

    val action = OneTimeActionWithParameter<Array<Any?>> {
        if (!it.contains(null)) block(it.requireNoNulls())
    }

    array.forEach { liveData ->
        liveData.observeForever {
            val newArray = arrayOfNulls<Any>(array.size)
            array.forEachIndexed { index, liveData ->
                newArray[index] = liveData.value
            }
            action.invoke(newArray)
        }
    }
}

