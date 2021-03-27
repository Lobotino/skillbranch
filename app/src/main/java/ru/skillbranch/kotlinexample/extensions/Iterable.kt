package ru.skillbranch.kotlinexample.extensions

object Iterable {

    fun<T> List<T>.dropLastUntil(predicate: (T) -> Boolean) : List<T>{
        val mutableResult = this.toMutableList()
        if (!isEmpty()) {
            for(i in size-1 downTo 0) {
                if(!predicate(this[i])) {
                    mutableResult.removeAt(i)
                } else {
                    mutableResult.removeAt(i)
                    return mutableResult
                }
            }
        }
        return emptyList()
    }
}