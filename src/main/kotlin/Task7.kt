class Item(val weight: Int, val cost: Int)

fun main() {
    val backpackMaxWeight = 5

    val items = ArrayList<Item>().apply {
        add(Item(weight = 1, cost = 6))
        add(Item(weight = 2, cost = 2))
        add(Item(weight = 3, cost = 4))
        add(Item(weight = 4, cost = 5))
    }

    bruteForce(backpackMaxWeight, items)
}

fun bruteForce(backpackMaxWeight: Int, items: List<Item>) {
    val weightsAndCosts = ArrayList<Pair<Int, Int>>()
    f("", items.size, items, weightsAndCosts, backpackMaxWeight)
    print(weightsAndCosts.maxWithOrNull(
        Comparator { o1, o2 ->
            return@Comparator o1.second - o2.second
        }
    ))
}

fun f(string: String, count: Int, items: List<Item>, weightsAndCosts: ArrayList<Pair<Int, Int>>, backpackMaxWeight: Int) {
    if (string.length < count) {
        f(string + "0", count, items, weightsAndCosts, backpackMaxWeight)
        f(string + "1", count, items, weightsAndCosts, backpackMaxWeight)
    } else {
        getWeightAndCost(string, items, backpackMaxWeight, weightsAndCosts)
    }
}

fun getWeightAndCost(string: String, items: List<Item>, backpackMaxWeight: Int, weightsAndCosts: ArrayList<Pair<Int, Int>>) {
    var weight = 0
    var cost = 0
    for ((index, value) in items.withIndex()) {
        if (string[index] == '1') {
            weight += value.weight
            cost += value.cost
        }
    }
    if (weight <= backpackMaxWeight)
        weightsAndCosts.add(Pair(weight, cost))
}