package com.alexchar_dev.socialrelationships.presentation.utils

class NavigationStack {
    private val elements: MutableList<BackStackElement> = mutableListOf()

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    fun push(item: BackStackElement) = run {
        val sameItem = elements.find { backStackElement -> backStackElement.name == item.name }

        if(sameItem != null) {
            val index = elements.indexOf(sameItem)
            elements.removeAt(index)
            elements.add(sameItem)
        }else {
            elements.add(item)
        }

    }

    fun pop() : BackStackElement? {
        val item = elements.lastOrNull()
        if (!isEmpty()){
            elements.removeAt(elements.size -1)
        }
        return item
    }

    fun peek() : BackStackElement? = elements.lastOrNull()

    fun previous() : BackStackElement? = elements[elements.size - 1]

    fun remove(element: BackStackElement) {
        elements.remove(element)
    }

    fun get(name: String) = elements.find { backStackElement ->
        backStackElement.name == name
    }

    override fun toString(): String  {
        val result : MutableList<Any> = mutableListOf()
        var list: MutableList<String>

        elements.forEach { backStackElement ->
            list = mutableListOf()
            var element: BackStackElement? = backStackElement
            while (element != null) {
                list.add(element.name)
                element = element.nextStack
            }
            if (list.size < 1) result.add(backStackElement.name) else result.add(list)
        }
        return result.toString()
    }
}

class BackStackElement(val name: String, val id: Int? = null, var nextStack: BackStackElement? = null)