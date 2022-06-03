package com.yapp.growth.data.mapper

inline fun <I, O> mapList(input: List<I>, mapListItem: (I) -> O): List<O> {
    return input.map { mapListItem(it) }
}

inline fun <I, O> mapNullInputList(input: List<I>?, mapListItem: (I) -> O): List<O> {
    return input?.map { mapListItem(it) } ?: emptyList()
}

inline fun <I, O> mapNullOutputList(input: List<I>, mapListItem: (I) -> O): List<O>? {
    return if (input.isEmpty()) null else input.map { mapListItem(it) }
}