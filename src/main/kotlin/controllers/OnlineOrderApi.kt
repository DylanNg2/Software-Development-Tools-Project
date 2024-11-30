package controllers

import persistence.Serializer
import models.OnlineOrder
import utils.isValidListIndex

class OnlineOrderApi (serializerType: Serializer){
    private  var serializer: Serializer = serializerType
    private  var orders = ArrayList<OnlineOrder>()

        fun add(order : OnlineOrder): List<OnlineOrder>{
             orders.add(order)
            return orders;
        }

    fun listOrder():String=
        if(orders.isEmpty()) "No orders stored"
        else formatListString(orders)

//This shows your order that is store into the system because convient

    fun deleteOrder(indexToDelete: Int): OnlineOrder? {
        return if (isValidIndex(indexToDelete)) {
            orders.removeAt(indexToDelete)
        } else null
    }
    fun updateOrder(indexToUpdate: Int, onlineOrder: OnlineOrder?): Boolean {
        val foundOrder = findOrder(indexToUpdate)

        if ((foundOrder != null) && ( onlineOrder != null)) {
            foundOrder.customerName = onlineOrder.customerName
            foundOrder.orderNum = onlineOrder.orderNum
            return true
        }
        return false
    }
    fun searchByDetails(searchString: String): String =
        formatListString(
            orders.filter { order -> order.customerName.contains(searchString, ignoreCase = true)
        })
    fun numberOfOrders() = orders.size

    fun findOrder(index: Int): OnlineOrder? {
        return if (isValidIndex(index)) {
            orders[index]
        } else null
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index = index, orders);
    }

    @Throws(Exception::class)
    fun load() {
        orders = serializer.read() as ArrayList<OnlineOrder>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(orders)
    }
    private fun formatListString(ordersToFormat: List<OnlineOrder>): String =
        ordersToFormat.joinToString(separator = "\n") { order ->
            orders.indexOf(order).toString() + ": " + order.toString()
        }
}
