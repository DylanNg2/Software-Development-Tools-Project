package utils

fun isValidListIndex(index: Int, list: List<Any>): Boolean {
    return (index >= 0 && index < list.size)
}