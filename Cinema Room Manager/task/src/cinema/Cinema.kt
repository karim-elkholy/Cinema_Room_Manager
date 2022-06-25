package cinema

const val FRONT_HALF_TICKET_COST = 10
const val BACK_HALF_TICKET_COST = 8

fun getPrice(rowNum: Int, seatNum: Int, seatLayout: MutableList<MutableList<String>>): Int {
    val numberOfRows = seatLayout.size
    val numberOfSeats = seatLayout[0].size

    val frontRowLimit = numberOfRows / 2

    return if (rowNum <= frontRowLimit) FRONT_HALF_TICKET_COST else BACK_HALF_TICKET_COST
}

fun buyTicket(seatLayout: MutableList<MutableList<String>>) {

    val numberOfRows = seatLayout.size
    val numberOfSeats = seatLayout[0].size

    while (true) {
        try {
            println("Enter a row number:")
            val rowNum = readln().toInt()
            if (rowNum > numberOfRows) throw IndexOutOfBoundsException("out of bounds")

            println("Enter a seat number in that row:")
            val seatNum = readln().toInt()
            if (seatNum > numberOfSeats) throw IndexOutOfBoundsException("out of bounds")

            println("Ticket price: $${getPrice(rowNum, seatNum, seatLayout)}")

            // Don't sell seats already sold
            if (seatLayout[rowNum - 1][seatNum - 1] == "B") { throw IllegalAccessException("Ticket already bought") }

            // Update the seat
            seatLayout[rowNum - 1][seatNum - 1] = "B"

            break
        } catch (e: NumberFormatException) {
            println("Invalid number written")
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
        } catch (e: IllegalAccessException) {
            println("That ticket has already been purchased!")
        }
    }
}

fun showSeatLayout(seatLayout: MutableList<MutableList<String>>) {

    println("Cinema:")
    print(" ")
    for (i in 1..seatLayout[0].size) { print(" $i") }
    println()

    for (i in 1..seatLayout.size) {
        println("$i ${seatLayout[i - 1].joinToString(" ")}")
    }

    println()
}

fun showStatistics(seatLayout: MutableList<MutableList<String>>) {
    var purchasedTickets = 0
    var percentageSold = 0.00
    var currentIncome = 0.00
    var totalIncome = 0.00

    // Get the number of rows
    val numberOfRows = seatLayout.size
    val numberOfSeats = seatLayout[0].size

    // Get the total number of seats
    val totalNumOfSeats = numberOfRows * numberOfSeats

    // Iterate through rows
    for (i in 1..numberOfRows) {

        // Iterate through seats
        for (j in 1..numberOfSeats) {
            // Get current price
            val currentPrice = getPrice(i, j, seatLayout)

            // If sold
            if (seatLayout[i - 1][j - 1] == "B") {
                // Update purchase total
                purchasedTickets += 1

                // Update current income
                currentIncome += currentPrice
            }

            // Update total income
            totalIncome += currentPrice
        }
    }

    if (purchasedTickets != 0) {
        percentageSold = purchasedTickets.toDouble() / totalNumOfSeats.toDouble()  * 100.00
    }

    val statisticsMessage = """
        Number of purchased tickets: $purchasedTickets
        Percentage: ${"%.2f".format(percentageSold)}%
        Current income: $${currentIncome.toInt()}
        Total income: $${totalIncome.toInt()}
    """.trimIndent()

    println(statisticsMessage)
}

fun main() {

    println("Enter the number of rows:")
    val numberOfRows = readln().toInt()

    println("Enter the number of seats in each row:")
    val numberOfSeats = readln().toInt()
    println()

    val seatLayout = MutableList(numberOfRows) { MutableList(numberOfSeats) { "S" } }

    while (true) {
        val prompt = """
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
        """.trimIndent()

        println(prompt)
        val choice = readln().toInt()
        println()

        when (choice) {
            0 -> break // exit the program
            1 -> showSeatLayout(seatLayout) // show seats
            2 -> buyTicket(seatLayout) // buy a ticket
            3 -> showStatistics(seatLayout) // buy a ticket
            else -> println("Unknown choice")
        }
    }
}
