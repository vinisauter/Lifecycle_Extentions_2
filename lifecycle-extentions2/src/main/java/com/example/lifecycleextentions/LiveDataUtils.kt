package com.example.lifecycleextentions

import androidx.lifecycle.*

fun <T> LiveData<T>.single(): SingleLiveData<T> {
    return SingleLiveData(this)
}

fun <T> LiveData<T>.singleEvent(): SingleLiveEvent<T> {
    return SingleLiveEvent(this)
}

fun <T, S> LiveData<S>.single(mapFunction: ReferenceFunction<S, SingleLiveData<T>>): SingleLiveData<T> {
    return SingleLiveData(this, mapFunction)
}

fun <T, S> LiveData<S>.singleEvent(mapFunction: ReferenceFunction<S, SingleLiveEvent<T>>): SingleLiveEvent<T> {
    return SingleLiveEvent(this, mapFunction)
}

fun <T> LiveData<T>.defaultIfNull(default: T): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    mutableLiveData.addSource(this) {
        mutableLiveData.value = it ?: default
    }
    return mutableLiveData
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}