package com.example.test.ui

//class KtTest {
data class A(val name: String, val age: Int)
data class B(val name: String, val age: Int, val address: String)


fun test(): A? {
    val a = A("张三", 18)
    if (a.age>100){
        return null
    }
    return a
}

//}
fun main() {
    val a = test()
    val b = a?.let {
        with(it) {
            B(name, age, "shanghai")
        }
    }
    println(a)
    println(a?.age)
    println(a?.name)

    println("----------------________-----------------")

    println(b)
    println(b?.age)
    println(b?.name)
    println(b?.address)
}