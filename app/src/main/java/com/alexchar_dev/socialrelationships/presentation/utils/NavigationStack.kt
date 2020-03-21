package com.alexchar_dev.socialrelationships.presentation.utils

class NavigationStack {
    private val elements: MutableList<Any> = mutableListOf()

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    fun push(item: Any) = run {
        if(elements.contains(item)) {
            elements.remove(item)
        }
        elements.add(item)
    }

    fun pop() : Any? {
        val item = elements.lastOrNull()
        if (!isEmpty()){
            elements.removeAt(elements.size -1)
        }
        return item
    }

    fun peek() : Any? = elements.lastOrNull()

    override fun toString(): String = elements.toString()
}