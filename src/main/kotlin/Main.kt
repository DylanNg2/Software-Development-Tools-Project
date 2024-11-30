package ie.setu

import controllers.OnlineOrderApi
import ie.setu.utils.readNextInt
import utils.*
import persistence.JsonSerializer
import java.lang.System.exit
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.File


fun main(){
    mainMenu()
    runMenu()
}
fun runMenu(){
    do{
        val option = mainMenu()
        when(option) {
            1 -> showMenu()
            2 -> createOrder()
            3 -> updateOrder()
            4 -> deleteOrder()
            5 -> saveOrder()
            6 -> archieveOrder()
            0 -> exit()
            else -> println("Invalid Option has entered: $option")
        }
    }while (true)
}
fun mainMenu(): Int {
    println("""
        >|---------------------------------
        >| ONLINE ORDER SYSTEM MENU
        >|---------------------------------
        >| 1) Show menu
        >| 2) Create a new order
        >| 3) Update an order
        >| 4) Delete an order
        >| 5) Save the order
        >| 6) Archive an order
        >| 0) Exit
        >|---------------------------------
        >| Enter your choice: 
    """.trimMargin(">"))
    return readNextInt(">")
}