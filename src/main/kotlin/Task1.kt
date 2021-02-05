import kotlin.math.pow
import kotlin.math.sqrt

/*
* Задание_1
* Назовём нетривиальным делителем натурального числа его делитель, не равный единице и самому числу.
* Найдите все натуральные числа, принадлежащие отрезку [106732567; 152673836] и имеющие ровно три нетривиальных делителя.
* Для каждого найденного числа запишите в ответе само число и его наибольший нетривиальный делитель.
* Найденные числа расположите в порядке возрастания.
* Оценить сложность алгоритма и время работы.
* 112550881 1092727
* 131079601 1225043
* 141158161 1295029
*/
fun main() {
    val m = System.currentTimeMillis()

    f(1, 152673836)

    println("Time: ${(System.currentTimeMillis() - m).toFloat() / 1000}")
}

fun f(a: Int, b: Int) {
    for (i in a..b) {                                  // n (3 + sqrt(n ^ 1/4))
        val t = sqrt(sqrt(i.toDouble()))               // 1
        if (t % 1 <= 0 && isPrime(t.toInt()))          // 1 + sqrt(t)
            println("$i, ${t.pow(3).toInt()}")      // 1
    }
}

// O(sqrt(num))
fun isPrime(num: Int): Boolean {
    for (i in 2..sqrt(num.toDouble()).toInt())
        if (num % i == 0)
            return false
    return true
}