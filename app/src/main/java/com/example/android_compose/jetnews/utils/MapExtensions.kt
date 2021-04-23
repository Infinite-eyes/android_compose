package com.example.android_compose.jetnews.utils


internal fun <E> MutableSet<E>.addOrRemove(element: E) {
    if(!add(element)){
        remove(element)
    }
}